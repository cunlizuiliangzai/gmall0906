package com.atguigu.gmall.user.service.serviceImpl;


import com.atguigu.gmall.user.bean.UserAddress;
import com.atguigu.gmall.user.bean.UserInfo;
import com.atguigu.gmall.user.mapper.UserInfoMapper;
import com.atguigu.gmall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoImpl implements UserService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public List<UserInfo> getUserInfoListAll() {

        return userInfoMapper.selectAll();
    }

    @Override
    public void addUser(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }

    @Override
    public void updateUser(UserInfo userInfo) {
        userInfoMapper.updateByPrimaryKey(userInfo);
    }

    @Override
    public List<UserAddress> getUserAddressList(String userId) {

        return null;
    }

    @Override
    public UserInfo login(UserInfo userInfo) {
        return null;
    }

    @Override
    public UserInfo verify(String userId) {
        return null;
    }
}
