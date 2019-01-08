package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.UserAddress;

import java.util.List;

public interface UserAddressService {
    List<UserAddress> getAllAddress(String userId);
}
