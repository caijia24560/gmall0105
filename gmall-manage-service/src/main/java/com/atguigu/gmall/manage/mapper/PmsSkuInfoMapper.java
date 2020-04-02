package com.atguigu.gmall.manage.mapper;

import java.util.List;

import com.atguigu.gmall.bean.PmsSkuInfo;

import tk.mybatis.mapper.common.Mapper;

public interface PmsSkuInfoMapper extends Mapper<PmsSkuInfo>{
    List<PmsSkuInfo> selectSkuSaleAttrValueListBySpu(String productId);

}
