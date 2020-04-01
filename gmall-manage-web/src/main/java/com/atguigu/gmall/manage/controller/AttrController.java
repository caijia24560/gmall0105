package com.atguigu.gmall.manage.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsBaseAttrInfo;
import com.atguigu.gmall.bean.PmsBaseAttrValue;
import com.atguigu.gmall.bean.PmsBaseSaleAttr;
import com.atguigu.gmall.service.AttrService;

@Controller
@CrossOrigin
public class AttrController  {

    @Reference
    AttrService attrService;

	@RequestMapping("baseSaleAttrList")
	@ResponseBody
	public List<PmsBaseSaleAttr> baseSaleAttrList(){

		List<PmsBaseSaleAttr> pmsBaseSaleAttrs = attrService.baseSaleAttrList();
		return pmsBaseSaleAttrs;
	}

    @RequestMapping("saveAttrInfo")
    @ResponseBody
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
		return attrService.saveAttrInfo(pmsBaseAttrInfo);
    }

	@RequestMapping("getAttrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue> getAttrValueList(String attrId){

        List<PmsBaseAttrValue> pmsBaseAttrInfos = attrService.getAttrValueList(attrId);
        return pmsBaseAttrInfos;
    }

	@RequestMapping("attrInfoList")
	@ResponseBody
	public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id){

		List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.attrInfoList(catalog3Id);
		return pmsBaseAttrInfos;
	}
}
