package com.atguigu.gmall.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.PmsProductImage;
import com.atguigu.gmall.bean.PmsProductInfo;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.bean.PmsProductSaleAttrValue;
import com.atguigu.gmall.manage.mapper.PmsProductImageMapper;
import com.atguigu.gmall.manage.mapper.PmsProductInfoMapper;
import com.atguigu.gmall.manage.mapper.PmsProductSaleAttrMapper;
import com.atguigu.gmall.manage.mapper.PmsProductSaleAttrValueMapper;
import com.atguigu.gmall.service.SpuService;

/**
 * @author cai
 * @Date 2020年03月27日 20:38:00
 */
@Service
public class SpuServiceImpl implements SpuService{
	@Autowired
	PmsProductInfoMapper pmsProductInfoMapper;
	@Autowired
	PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
	@Autowired
	PmsProductImageMapper pmsProductImageMapper;
	@Autowired
	PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;

	@Override
	public List<PmsProductInfo> spuList(String catalog3Id){
		PmsProductInfo pmsProductInfo = new PmsProductInfo();
		pmsProductInfo.setCatalog3Id(catalog3Id);

		return pmsProductInfoMapper.select(pmsProductInfo);
	}

	@Override
	public List<PmsProductSaleAttr> spuSaleAttrList(String spuId){
		PmsProductSaleAttr productSaleAttr = new PmsProductSaleAttr();
		productSaleAttr.setProductId(spuId);
		List<PmsProductSaleAttr> saleAttrList = pmsProductSaleAttrMapper.select(productSaleAttr);
		for(PmsProductSaleAttr pmsProductSaleAttr : saleAttrList){
			PmsProductSaleAttrValue pmsProductSaleAttrValue = new PmsProductSaleAttrValue();
			pmsProductSaleAttrValue.setProductId(pmsProductSaleAttr.getProductId());
			//销售属性ID用的是系统字典表中的ID,不是产品销售属性中的ID
			pmsProductSaleAttrValue.setSaleAttrId(pmsProductSaleAttr.getSaleAttrId());
			List<PmsProductSaleAttrValue> valueList = pmsProductSaleAttrValueMapper.select(pmsProductSaleAttrValue);
			pmsProductSaleAttr.setSpuSaleAttrValueList(valueList);
		}

		return saleAttrList;
	}

	@Transactional
	@Override
	public void saveSpuInfo(PmsProductInfo pmsProductInfo){
		//保存商品信息
		pmsProductInfoMapper.insertSelective(pmsProductInfo);
		//生成商品ID
		String productInfoId = pmsProductInfo.getId();
		//保存商品图片信息
		List<PmsProductImage> imageList = pmsProductInfo.getSpuImageList();
		imageList.forEach(image -> {
			image.setProductId(productInfoId);
			pmsProductImageMapper.insertSelective(image);
		});
		//保存销售属性信息
		List<PmsProductSaleAttr> attrList = pmsProductInfo.getSpuSaleAttrList();
		attrList.forEach(attr -> {
			attr.setProductId(productInfoId);
			pmsProductSaleAttrMapper.insertSelective(attr);
			//保存销售属性value
			List<PmsProductSaleAttrValue> valueList = attr.getSpuSaleAttrValueList();
			for(PmsProductSaleAttrValue value : valueList){
				value.setProductId(productInfoId);
				pmsProductSaleAttrValueMapper.insertSelective(value);
			}
		});

	}

	@Override
	public List<PmsProductImage> spuImageList(String spuId){
		PmsProductImage pmsProductImage = new PmsProductImage();
		pmsProductImage.setProductId(spuId);
		List<PmsProductImage> imageList = pmsProductImageMapper.select(pmsProductImage);

		return imageList;
	}

	@Override
	public List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId,String skuId){

		//        PmsProductSaleAttr pmsProductSaleAttr = new PmsProductSaleAttr();
		//        pmsProductSaleAttr.setProductId(productId);
		//        List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.select(pmsProductSaleAttr);
		//
		//        for (PmsProductSaleAttr productSaleAttr : pmsProductSaleAttrs) {
		//            String saleAttrId = productSaleAttr.getSaleAttrId();
		//
		//            PmsProductSaleAttrValue pmsProductSaleAttrValue = new PmsProductSaleAttrValue();
		//            pmsProductSaleAttrValue.setSaleAttrId(saleAttrId);
		//            pmsProductSaleAttrValue.setProductId(productId);
		//            List<PmsProductSaleAttrValue> pmsProductSaleAttrValues = pmsProductSaleAttrValueMapper.select(pmsProductSaleAttrValue);
		//
		//            productSaleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValues);
		//
		//        }

		List<PmsProductSaleAttr> pmsProductSaleAttrs = pmsProductSaleAttrMapper.selectSpuSaleAttrListCheckBySku(productId, skuId);
		return pmsProductSaleAttrs;

	}
}
