package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.BaseAttrInfo;
import com.atguigu.gmall.bean.BaseCatalog1;
import com.atguigu.gmall.bean.BaseCatalog2;
import com.atguigu.gmall.bean.BaseCatalog3;
import com.atguigu.gmall.service.AttrService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AttrController {

    @Reference
    AttrService attrService;

    @ResponseBody
    @RequestMapping("getCatalog1")
    public List<BaseCatalog1> getCatalog1(){
        List<BaseCatalog1> baseCatalog1s = attrService.getCatalog1();
        return baseCatalog1s;
    }

    @ResponseBody
    @RequestMapping("getCatalog2")
    public List<BaseCatalog2> getCatalog2(String catalog1Id){
        List<BaseCatalog2> baseCatalog2s = attrService.getCatalog2(catalog1Id);
        return baseCatalog2s;
    }

    @ResponseBody
    @RequestMapping("getCatalog3")
    public List<BaseCatalog3> getCatalog3(String catalog2Id){
        List<BaseCatalog3> baseCatalog3s = attrService.getCatalog3(catalog2Id);
        return baseCatalog3s;
    }

    @ResponseBody
    @RequestMapping("getAttrList")
    public List<BaseAttrInfo> getAttrList(String catalog3Id){
        List<BaseAttrInfo> baseAttrInfo = attrService.getAttrList(catalog3Id);
        return baseAttrInfo;
    }

    @ResponseBody
    @RequestMapping("saveAttr")
    public String saveAttr(BaseAttrInfo baseAttrInfo){
        attrService.saveAttr(baseAttrInfo);
        return "SUCCESS";
    }

}
