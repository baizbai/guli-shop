package com.ljj.guli.shop.service;

import com.ljj.guli.shop.bean.PmsBaseAttrInfo;
import com.ljj.guli.shop.bean.PmsBaseAttrValue;

import java.util.List;

/**
 * @author liujingjie
 * @date 2019/8/25 18:19
 * @since V1.0
 **/
public interface AttrInfoService {

    List<PmsBaseAttrInfo> attrInfoList(String catalog3);

    String saveAttrInfo( PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);
}
