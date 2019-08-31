package com.ljj.guli.shop.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ljj.guli.shop.bean.*;
import com.ljj.guli.shop.service.AttrInfoService;
import com.ljj.guli.shop.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;
import java.util.List;

/**
 * @author liujingjie
 * @date 2019/8/25 15:51
 * @since V1.0
 **/
@Controller
@CrossOrigin
public class AttrController {

    @Reference
    AttrInfoService attrInfoService;

    @RequestMapping("attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id){
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrInfoService.attrInfoList(catalog3Id);
        return pmsBaseAttrInfos;
    }

    @RequestMapping("saveAttrInfo")
    @ResponseBody
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){

        String success = attrInfoService.saveAttrInfo(pmsBaseAttrInfo);

        return success;
    }

    @RequestMapping("getAttrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue> getAttrValueList(String attrId){
        List<PmsBaseAttrValue> attrValueList = attrInfoService.getAttrValueList(attrId);
        return attrValueList;
    }

}
