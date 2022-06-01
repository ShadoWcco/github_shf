package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserInfo;

/**
 * Date : 2022/5/25
 * Author : cc
 * Description :
 */
public interface UserInfoService extends BaseService<UserInfo> {

    UserInfo getByPhone(String phone);

}
