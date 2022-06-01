package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Admin;

import java.util.List;

/**
 * Date : 2022/5/17
 * Author : cc
 * Description :
 */
public interface AdminMapper extends BaseMapper<Admin> {

    List<Admin> findAll();

    Admin getByUsername(String username);

}
