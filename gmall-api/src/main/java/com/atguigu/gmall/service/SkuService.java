package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.SkuInfo;

import java.util.List;

public interface SkuService {
    public List<SkuInfo> getSkuByspuId(String spuId);

    void saveSku(SkuInfo skuInfo);

    SkuInfo item(String skuId);

    List<SkuInfo> getSkuSaleAttrValueListBySpu(String spuId);

    List<SkuInfo> skuListByCatalog3Id(String s);


    SkuInfo getSkuById(String skuId);
}
