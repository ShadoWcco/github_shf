package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.UserInfo;

/**
 * Date : 2022/5/25
 * Author : cc
 * Description :
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    UserInfo getByPhone(String phone);

}
