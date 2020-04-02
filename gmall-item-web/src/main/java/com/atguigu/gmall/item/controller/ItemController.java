package com.atguigu.gmall.item.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.service.SkuService;

@Controller
public class ItemController {
	@Reference
	SkuService skuService;

    @RequestMapping("index")
    public String index(ModelMap modelMap){

        List<String> list = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            list.add("循环数据"+i);
        }

        modelMap.put("list",list);
        modelMap.put("hello","hello thymeleaf !!");

        modelMap.put("check","0");


        return "index";
    }

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId , ModelMap modelMap){
		PmsSkuInfo pmsSkuInfo = skuService.getSkuById(skuId);
		modelMap.put("skuInfo",pmsSkuInfo);
        return "item";
    }


}
