package com.atguigu.gmall.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atguigu.gmall.bean.PmsProductSaleAttr;

import tk.mybatis.mapper.common.Mapper;

public interface PmsProductSaleAttrMapper extends Mapper<PmsProductSaleAttr>{
    List<PmsProductSaleAttr> selectSpuSaleAttrListCheckBySku(@Param("productId") String productId, @Param("skuId") String skuId);
}
