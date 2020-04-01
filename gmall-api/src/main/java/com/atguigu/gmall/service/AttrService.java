package com.atguigu.gmall.service;

import java.util.List;

import com.atguigu.gmall.bean.PmsBaseAttrInfo;
import com.atguigu.gmall.bean.PmsBaseAttrValue;
import com.atguigu.gmall.bean.PmsBaseSaleAttr;


public interface AttrService {
	List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);


	/**
	 * 保存商品属性
	 * @return
	 */
	String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

	/**
	 * 根据属性id获取所有属性value
	 * @param attrId
	 * @return
	 */
	List<PmsBaseAttrValue> getAttrValueList(String attrId);

	/**
	 * 通用销售属性字典列表
	 * @return
	 */
	List<PmsBaseSaleAttr> baseSaleAttrList();
}
