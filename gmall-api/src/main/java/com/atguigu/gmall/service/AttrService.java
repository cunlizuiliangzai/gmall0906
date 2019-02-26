package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.*;

import java.util.List;

public interface AttrService {
    List<BaseCatalog1> getCatalog1();

    List<BaseCatalog2> getCatalog2(String catalog1Id);

    List<BaseCatalog3> getCatalog3(String catalog2Id);

    List<BaseAttrInfo> getAttrList(String catalog3Id);

    void saveAttr(BaseAttrInfo baseAttrInfo);

    List<BaseAttrValue> getAttrValueById(String attrId);

    void saveEditAttr(BaseAttrInfo baseAttrInfo);

    void removeAttrInfo(List<String> attrId);


    List<BaseAttrInfo> getAttrListByValueIds(String join);
}
