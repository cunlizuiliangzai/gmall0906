package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.bean.UserInfo;

import java.util.List;

public interface UserService {

    public List<UserInfo> getUserInfoListAll();

    public void addUser(UserInfo userInfo);

    public void updateUser(UserInfo userInfo);

    public List<UserAddress> getUserAddressList(String userId);

    public UserInfo login(UserInfo userInfo);

    public UserInfo verify(String userId);


    void addUserCache(UserInfo userLogin);

    UserAddress getAddressById(String addressId);
}
