package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.UserInfo;
import com.atguigu.mapper.UserInfoMapper;
import com.atguigu.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Date : 2022/5/25
 * Author : cc
 * Description :
 */
@Service(interfaceClass = UserInfoService.class)
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    protected BaseMapper<UserInfo> getEntityMapper() {
        return userInfoMapper;
    }

    @Override
    public UserInfo getByPhone(String phone) {
        return userInfoMapper.getByPhone(phone);
    }
}
