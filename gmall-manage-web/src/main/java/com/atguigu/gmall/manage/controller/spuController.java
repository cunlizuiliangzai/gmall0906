package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.SpuInfo;
import com.atguigu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class spuController {
    @Reference
    SpuService spuService;

    @ResponseBody
    @RequestMapping("spulist")
    public List<SpuInfo> spulist(String catalog3Id){
        List<SpuInfo> spulist = spuService.queryspulist(catalog3Id);
        return spulist;
    }
}
