package com.atguigu.gmall.user.serviceImpl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.bean.UserInfo;
import com.atguigu.gmall.user.mapper.UserInfoMapper;
import com.atguigu.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


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
