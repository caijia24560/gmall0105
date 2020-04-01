package com.atguigu.gmall.manage.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsProductImage;
import com.atguigu.gmall.bean.PmsProductInfo;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.manage.controller.util.PmsUploadUtil;
import com.atguigu.gmall.service.SpuService;

@Controller
@CrossOrigin
public class SpuController {

	@Reference
	SpuService spuService;

	@RequestMapping("fileUpload")
	@ResponseBody
	public String fileUpload(@RequestParam("file") MultipartFile multipartFile){
		// 将图片或者音视频上传到分布式的文件存储系统
		String imgUrl = PmsUploadUtil.uploadImage(multipartFile);
		// 将图片的存储路径返回给页面
		// String imgUrl = "https://m.360buyimg.com/babel/jfs/t5137/20/1794970752/352145/d56e4e94/591417dcN4fe5ef33.jpg";

		return imgUrl;
	}

	@RequestMapping("spuSaleAttrList")
	@ResponseBody
	public List<PmsProductSaleAttr> spuSaleAttrList(String spuId){

		List<PmsProductSaleAttr> pmsProductSaleAttrs = spuService.spuSaleAttrList(spuId);
		return pmsProductSaleAttrs;
	}

	@RequestMapping("spuImageList")
	@ResponseBody
	public List<PmsProductImage> spuImageList(String spuId){

		List<PmsProductImage> pmsProductImages = spuService.spuImageList(spuId);
		return pmsProductImages;
	}

	@RequestMapping("saveSpuInfo")
	@ResponseBody
	public String saveSpuInfo(@RequestBody  PmsProductInfo pmsProductInfo){
		spuService.saveSpuInfo(pmsProductInfo);

		return "success";
	}

	@RequestMapping("spuList")
	@ResponseBody
	public List<PmsProductInfo> spuList(String catalog3Id){

		List<PmsProductInfo> pmsProductInfos = spuService.spuList(catalog3Id);

		return pmsProductInfos;
	}
}
