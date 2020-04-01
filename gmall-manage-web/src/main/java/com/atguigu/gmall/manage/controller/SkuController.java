package com.atguigu.gmall.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.service.PmsSkuService;

/**
 * @author cai
 * @Date 2020年04月01日 22:03:00
 */
@Controller
@CrossOrigin
public class SkuController{

	@Reference
	PmsSkuService pmsSkuService;

	@RequestMapping("saveSkuInfo")
	@ResponseBody
	public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo){
       pmsSkuService.saveSkuInfo(pmsSkuInfo);

		return "success";
	}
}
