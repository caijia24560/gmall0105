package com.atguigu.gmall.service;

import java.util.List;

import com.atguigu.gmall.bean.PmsProductImage;
import com.atguigu.gmall.bean.PmsProductInfo;
import com.atguigu.gmall.bean.PmsProductSaleAttr;

/**
 * @author cai
 * @Date 2020年03月27日 20:38:00
 */
public interface SpuService{

	List<PmsProductInfo> spuList(String catalog3Id);

	List<PmsProductSaleAttr> spuSaleAttrList(String spuId);

	void saveSpuInfo(PmsProductInfo pmsProductInfo);

	List<PmsProductImage> spuImageList(String spuId);
}
