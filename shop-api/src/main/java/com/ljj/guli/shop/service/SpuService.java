package com.ljj.guli.shop.service;


import com.ljj.guli.shop.bean.PmsProductImage;
import com.ljj.guli.shop.bean.PmsProductInfo;
import com.ljj.guli.shop.bean.PmsProductSaleAttr;

import java.util.List;


public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    void saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);

    List<PmsProductImage> spuImageList(String spuId);
}
