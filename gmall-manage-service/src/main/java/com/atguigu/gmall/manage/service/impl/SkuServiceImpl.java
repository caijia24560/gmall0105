package com.atguigu.gmall.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.PmsSkuAttrValue;
import com.atguigu.gmall.bean.PmsSkuImage;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.bean.PmsSkuSaleAttrValue;
import com.atguigu.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuImageMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuInfoMapper;
import com.atguigu.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.atguigu.gmall.service.SkuService;

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
		PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
		pmsSkuInfo.setId(skuId);
		pmsSkuInfo = pmsSkuInfoMapper.selectOne(pmsSkuInfo);
		PmsSkuImage pmsSkuImage = new PmsSkuImage();
		pmsSkuImage.setSkuId(skuId);
		List<PmsSkuImage> imageList = pmsSkuImageMapper.select(pmsSkuImage);
		pmsSkuInfo.setSkuImageList(imageList);

		return pmsSkuInfo;
	}
}
