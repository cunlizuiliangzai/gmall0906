package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.BaseSaleAttr;
import com.atguigu.gmall.bean.SpuImage;
import com.atguigu.gmall.bean.SpuInfo;
import com.atguigu.gmall.bean.SpuSaleAttr;

import java.util.List;

public interface SpuService {
    List<SpuInfo> queryspulist(String catalog3Id);

    List<BaseSaleAttr> baseSaleAttrList();

    void saveSpu(SpuInfo spuInfo);

    List<SpuSaleAttr> querySpuSaleAttrList(String spuId);

    List<SpuImage> querySpuImageList(String spuId);

    List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(String spuId, String skuId);
}
