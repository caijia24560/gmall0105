package com.atguigu.gmall.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.PmsBaseAttrInfo;
import com.atguigu.gmall.bean.PmsBaseAttrValue;
import com.atguigu.gmall.bean.PmsBaseSaleAttr;
import com.atguigu.gmall.manage.mapper.PmsBaseAttrInfoMapper;
import com.atguigu.gmall.manage.mapper.PmsBaseAttrValueMapper;
import com.atguigu.gmall.manage.mapper.PmsBaseSaleAttrMapper;
import com.atguigu.gmall.service.AttrService;

@Service
public class AttrServiceImpl implements AttrService {

	@Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
	@Autowired
	PmsBaseAttrValueMapper pmsBaseAttrValueMapper;
	@Autowired
	PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;

    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {

        PmsBaseAttrInfo pmsBaseAttrInfo = new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoMapper.select(pmsBaseAttrInfo);
		for(PmsBaseAttrInfo baseAttrInfo : pmsBaseAttrInfos){
			PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
			pmsBaseAttrValue.setAttrId(baseAttrInfo.getId());
			List<PmsBaseAttrValue> valueList = pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
			baseAttrInfo.setAttrValueList(valueList);
		}

        return pmsBaseAttrInfos;
    }

	/**
	 * 保存商品属性
	 */
	@Override
	public String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo){
		//保存属性,insertSelective作用是不保存空值
		pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);

		List<PmsBaseAttrValue> valueList = pmsBaseAttrInfo.getAttrValueList();
		for(PmsBaseAttrValue value : valueList){
			value.setAttrId(pmsBaseAttrInfo.getId());
			//保存属性value
			pmsBaseAttrValueMapper.insertSelective(value);
		}
		return "success";
	}

	/**
	 * 根据属性id获取所有属性value
	 * @param attrId
	 * @return
	 */
	@Override
	public List<PmsBaseAttrValue> getAttrValueList(String attrId){
		PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
		pmsBaseAttrValue.setAttrId(attrId);

		return pmsBaseAttrValueMapper.select(pmsBaseAttrValue);
	}

	/**
	 * 通用销售属性字典列表
	 * @return
	 */
	@Override
	public List<PmsBaseSaleAttr> baseSaleAttrList(){
		return pmsBaseSaleAttrMapper.selectAll();
	}
}
