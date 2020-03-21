package com.atguigu.gmall.service;

import java.util.List;

import com.atguigu.gmall.bean.PmsBaseAttrInfo;


public interface AttrService {
    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);
}
