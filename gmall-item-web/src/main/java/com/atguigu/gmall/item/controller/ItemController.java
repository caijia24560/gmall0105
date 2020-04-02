package com.atguigu.gmall.item.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.bean.PmsSkuSaleAttrValue;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.service.SpuService;

@Controller
public class ItemController{
	@Reference
	SkuService skuService;
	@Reference
	SpuService spuService;

	@RequestMapping("index")
	public String index(ModelMap modelMap){

		List<String> list = new ArrayList<>();
		for(int i = 0 ; i < 5 ; i++){
			list.add("循环数据" + i);
		}

		modelMap.put("list", list);
		modelMap.put("hello", "hello thymeleaf !!");

		modelMap.put("check", "0");


		return "index";
	}

	@RequestMapping("{skuId}.html")
	public String item(@PathVariable String skuId, ModelMap modelMap){
		PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId);
		modelMap.put("skuInfo", pmsSkuInfo);
		//销售属性列表
		List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.
				spuSaleAttrListCheckBySku(pmsSkuInfo.getProductId(), pmsSkuInfo.getId());
		modelMap.put("spuSaleAttrListCheckBySku", pmsProductSaleAttrs);
		//查询当前SKU的spu的其他sku集合的hash表结构
		List<PmsSkuInfo> pmsSkuInfos = skuService.getSkuSaleAttrValueListBySpu(pmsSkuInfo.getProductId());
		HashMap<String, String> map = new HashMap<>();

		for(PmsSkuInfo skuInfo : pmsSkuInfos){
			String k = "";
			String id = skuInfo.getId();
			List<PmsSkuSaleAttrValue> valueList = skuInfo.getSkuSaleAttrValueList();
			for(PmsSkuSaleAttrValue value : valueList){
				k += value.getSaleAttrValueId() + "|";
			}
			map.put(k.substring(0, k.length() - 1), id);
		}
		//将hash的销售属性表放到页面去
		String hashJsonStr = JSON.toJSONString(map);
		modelMap.put("skuSaleAttrHashJsonStr", hashJsonStr);

		return "item";
	}


}
