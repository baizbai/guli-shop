package com.ljj.guli.shop.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.ljj.guli.shop.bean.PmsBaseCatalog1;
import com.ljj.guli.shop.bean.PmsBaseCatalog2;
import com.ljj.guli.shop.bean.PmsBaseCatalog3;
import com.ljj.guli.shop.manage.mapper.PmsBaseCatalog1Mapper;
import com.ljj.guli.shop.manage.mapper.PmsBaseCatalog2Mapper;
import com.ljj.guli.shop.manage.mapper.PmsBaseCatalog3Mapper;
import com.ljj.guli.shop.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author liujingjie
 * @date 2019/8/25 16:06
 * @since V1.0
 **/
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;

    @Autowired
    PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;

    @Autowired
    PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;

    @Override
    public List<PmsBaseCatalog1> getCatalog() {
        return pmsBaseCatalog1Mapper.selectAll();
    }

    @Override
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {
        PmsBaseCatalog2 pbc2 = new PmsBaseCatalog2();
        pbc2.setCatalog1Id(catalog1Id);
        List<PmsBaseCatalog2> PmsBaseCatalog2s = pmsBaseCatalog2Mapper.select(pbc2);
        return PmsBaseCatalog2s;
    }

    @Override
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {
        PmsBaseCatalog3 pbc3 = new PmsBaseCatalog3();
        pbc3.setCatalog2Id(catalog2Id);
        List<PmsBaseCatalog3> PmsBaseCatalog3s = pmsBaseCatalog3Mapper.select(pbc3);
        return PmsBaseCatalog3s;
    }

}
