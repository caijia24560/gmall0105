package com.atguigu.gmall.service;

import java.util.List;

import com.atguigu.gmall.bean.PmsSkuInfo;

/**
 * @author cai
 * @Date 2020年04月01日 22:11:00
 */
public interface SkuService{

	String saveSkuInfo(PmsSkuInfo pmsSkuInfo);

	PmsSkuInfo getSkuById(String skuId);

	List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId);
}
