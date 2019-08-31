package com.ljj.guli.shop.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ljj.guli.shop.bean.PmsBaseAttrInfo;
import com.ljj.guli.shop.bean.PmsBaseAttrValue;
import com.ljj.guli.shop.manage.mapper.PmsBaseAttrIValueMapper;
import com.ljj.guli.shop.manage.mapper.PmsBaseAttrInfoMapper;
import com.ljj.guli.shop.service.AttrInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

/**
 * @author liujingjie
 * @date 2019/8/25 18:21
 * @since V1.0
 **/
@Service
public class AttrInfoServiceImpl implements AttrInfoService {

    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Autowired
    PmsBaseAttrIValueMapper pmsBaseAttrIValueMapper;

    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3) {
        PmsBaseAttrInfo pmsBaseAttrInfo = new PmsBaseAttrInfo();
        pmsBaseAttrInfo.setCatalog3Id(catalog3);
        List<PmsBaseAttrInfo> PmsBaseAttrInfos = pmsBaseAttrInfoMapper.select(pmsBaseAttrInfo);
        return PmsBaseAttrInfos;
    }

    @Override
    public String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {

        String id = pmsBaseAttrInfo.getId();
        // 新增
        if(Objects.isNull(pmsBaseAttrInfo.getId())) {
            pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                pmsBaseAttrIValueMapper.insertSelective(pmsBaseAttrValue);
            }
        }
        // 修改
        else{
            // 先根据id更新 pmsBaseAttrInfo
            Example example = new Example(PmsBaseAttrInfo.class);
            example.createCriteria().andEqualTo("id",id);
            pmsBaseAttrInfoMapper.updateByExampleSelective(pmsBaseAttrInfo,example);

            // 在删除pmsBaseAttrInfo对应的属性
            PmsBaseAttrValue value = new PmsBaseAttrValue();
            value.setAttrId(id);
            pmsBaseAttrIValueMapper.delete(value);

            // 在将新的属性添加进数据库以达到更新的目的
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            for (PmsBaseAttrValue v : attrValueList){
                v.setAttrId(id);
                pmsBaseAttrIValueMapper.insertSelective(v);
            }
        }

        return "success";
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {

        PmsBaseAttrValue value = new PmsBaseAttrValue();
        value.setAttrId(attrId);
        List<PmsBaseAttrValue> select = pmsBaseAttrIValueMapper.select(value);

        return select;
    }
}
