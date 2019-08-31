package com.ljj.guli.shop.service;

import com.ljj.guli.shop.bean.PmsBaseCatalog1;
import com.ljj.guli.shop.bean.PmsBaseCatalog2;
import com.ljj.guli.shop.bean.PmsBaseCatalog3;

import java.util.List;

/**
 * @author liujingjie
 * @date 2019/8/25 16:06
 * @since V1.0
 **/
public interface CatalogService {
    /**
     * 获取全部 catalog1数据
     * @return
     */
    List<PmsBaseCatalog1> getCatalog();

    /**
     * 获取 catalog2
     * @return
     */
    List<PmsBaseCatalog2> getCatalog2(String catalog1Id);

    /**
     * 获取 catalog2
     * @return
     */
    List<PmsBaseCatalog3> getCatalog3(String catalog2Id);

}
