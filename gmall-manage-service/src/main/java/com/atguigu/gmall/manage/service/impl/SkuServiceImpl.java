package com.atguigu.gmall.manage.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.PmsSkuAttrValue;
import com.atguigu.gmall.bean.PmsSkuImage;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.bean.PmsSkuSaleAttrValue;
import com.atguigu.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuImageMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuInfoMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.util.RedisUtil;

import redis.clients.jedis.Jedis;

/**
 * @author cai
 * @Date 2020年04月01日 22:14:00
 */
@Service
public class SkuServiceImpl implements SkuService{
	@Autowired
	PmsSkuInfoMapper pmsSkuInfoMapper;
	@Autowired
	PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
	@Autowired
	PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
	@Autowired
	PmsSkuImageMapper pmsSkuImageMapper;
	@Autowired
	RedisUtil redisUtil;

	@Transactional
	@Override
	public String saveSkuInfo(PmsSkuInfo pmsSkuInfo){
		//保存商品SKU信息
		pmsSkuInfo.setProductId(pmsSkuInfo.getSpuId());
		pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
		//获取商品SKU的ID
		String skuInfoId = pmsSkuInfo.getId();
		//保存商品SKU属性信息
		List<PmsSkuAttrValue> attrValues = pmsSkuInfo.getSkuAttrValueList();
		for(PmsSkuAttrValue attrValue : attrValues){
			attrValue.setSkuId(skuInfoId);
			pmsSkuAttrValueMapper.insertSelective(attrValue);
		}
		//保存商品SKU销售属性信息
		List<PmsSkuSaleAttrValue> saleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
		for(PmsSkuSaleAttrValue saleAttrValue : saleAttrValueList){
			saleAttrValue.setSkuId(skuInfoId);
			pmsSkuSaleAttrValueMapper.insertSelective(saleAttrValue);
		}
		//保存sku图片信息
		List<PmsSkuImage> imageList = pmsSkuInfo.getSkuImageList();
		for(PmsSkuImage skuImage : imageList){
			skuImage.setSkuId(skuInfoId);
			pmsSkuImageMapper.insertSelective(skuImage);
		}

		return "success";
	}

	@Override
	public PmsSkuInfo getSkuById(String skuId){
		PmsSkuInfo skuInfo = null;
		//连接缓存
		Jedis jedis = redisUtil.getJedis();
		//查询缓存
		String skuKey = "sku:"+skuId+":info";
		String skuJson = jedis.get(skuKey);
		//如果没有,查询数据库
		if(StringUtils.isNoneBlank(skuJson)){
			skuInfo = JSON.parseObject(skuJson,PmsSkuInfo.class);
		}else {
			//数据库查询结果,并存入redis
			//设置分布式锁
			String rtn = jedis.set("sku:" + skuId + ":lock", "1", "nx", "px", 10);
			if(StringUtils.isNotBlank(rtn) && "OK".equals(rtn)){
				//设置成功,有权在10秒钟过期时间访问数据库
				skuInfo = getSkuById1(skuId);
				if(skuInfo!=null){
					jedis.set(skuKey,JSON.toJSONString(skuInfo));
				}else {
					//数据库不存在改sku,为防止缓存穿透,将null和空串设置给redis
					jedis.setex(skuKey,60*3,JSON.toJSONString(""));
				}
			}else {
				//设置失败 自旋(该线程睡眠时间后重新访问本方法)
				try{
					Thread.sleep(3000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				return getSkuById(skuId);
			}

		}

		return skuInfo;
	}


	// @Override
	public PmsSkuInfo getSkuById1(String skuId){
		PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
		pmsSkuInfo.setId(skuId);
		pmsSkuInfo = pmsSkuInfoMapper.selectOne(pmsSkuInfo);
		PmsSkuImage pmsSkuImage = new PmsSkuImage();
		pmsSkuImage.setSkuId(skuId);
		List<PmsSkuImage> imageList = pmsSkuImageMapper.select(pmsSkuImage);
		pmsSkuInfo.setSkuImageList(imageList);

		return pmsSkuInfo;
	}

	@Override
	public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId){
		List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);
		return pmsSkuInfos;
	}
}
