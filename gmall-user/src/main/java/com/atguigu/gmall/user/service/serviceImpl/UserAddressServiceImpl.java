package com.atguigu.gmall.user.service.serviceImpl;

import com.atguigu.gmall.user.bean.UserAddress;
import com.atguigu.gmall.user.mapper.UserAddressMapper;
import com.atguigu.gmall.user.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    UserAddressMapper userAddressMapper;
    @Override
    public List<UserAddress> getAllAddress() {

        return null;
    }
}
