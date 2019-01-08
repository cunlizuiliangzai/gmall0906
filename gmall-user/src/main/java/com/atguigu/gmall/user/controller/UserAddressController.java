package com.atguigu.gmall.user.controller;

import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserAddressController {
    @Autowired
    UserAddressService userAddressService;

    @ResponseBody
    @RequestMapping("/get/Address/List/{userId}")
    public List<UserAddress> getAddressList(@PathVariable("userId") String userId){
        List<UserAddress> userAddressList = userAddressService.getAllAddress(userId);
        return userAddressList;
    }
}
