package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Permission;

import java.util.List;

/**
 * Date : 2022/5/26
 * Author : cc
 * Description :
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 查询所有权限列表
     * @return
     */
    List<Permission> findAll();

    /**
     * 根据adminId查询权限列表
     * @param adminId
     * @return
     */
    List<Permission> findPermissionListByAdminId(Long adminId);

    /**
     * 查询所有权限code
     * @return
     */
    List<String> findAllCodePermission();

    /**
     * 根据adminId查询权限code列表
     * @param adminId
     * @return
     */
    List<String> findCodePermissionListByAdminId(Long adminId);
}
