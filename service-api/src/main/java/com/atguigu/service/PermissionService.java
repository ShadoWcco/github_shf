package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * Date : 2022/5/26
 * Author : cc
 * Description :
 */
public interface PermissionService extends BaseService<Permission> {

    /**
     * 根据角色获取权限数据
     * @param roleId
     * @return
     */
    List<Map<String,Object>> findPermissionByRoleId(Long roleId);

    /**
     * 保存角色权限
     * @param roleId
     * @param permissionIdList
     */
    void saveRolePermission(Long roleId,List<Long> permissionIdList);

    /**
     * 根据adminId查询用户的所有权限
     * @param adminId
     * @return
     */
    List<Permission> findMenuPermissionByAdminId(Long adminId);

    /**
     * 查询所有菜单
     * @return
     */
    List<Permission> findAllMenu();

    /**
     * 根据adminId查询权限code列表
     * @param adminId
     * @return
     */
    List<String> findCodePermissionListByAdminId(Long adminId);
}
