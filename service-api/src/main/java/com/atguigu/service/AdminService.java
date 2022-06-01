package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Admin;
import com.atguigu.entity.AdminRole;

import java.util.List;
import java.util.Map;

/*
 * Date : 2022/5/17
 * Author : cc
 * Description :
 */

public interface AdminService extends BaseService<Admin> {

    List<Admin> findAll();

    Admin getByUsername(String username);

}
