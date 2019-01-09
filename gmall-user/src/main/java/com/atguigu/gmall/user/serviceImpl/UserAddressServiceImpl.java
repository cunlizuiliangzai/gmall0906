package com.atguigu.gmall.user.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.user.mapper.UserAddressMapper;
import com.atguigu.gmall.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
@Service
public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> getAllAddress(String userId) {
       UserAddress userAddress = new UserAddress();
       userAddress.setUserId(userId);
       List<UserAddress> addressList =  userAddressMapper.select(userAddress);
        return addressList;
    }
}
