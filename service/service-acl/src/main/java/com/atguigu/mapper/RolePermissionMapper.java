package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Date : 2022/5/26
 * Author : cc
 * Description :
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    /**
     * 根据角色id获取查询权限id集合
     * @param roleId
     * @return
     */
    List<Long> findPermissionIdListByRoleId(Long roleId);

    /**
     * 删除角色的权限
     * @param roleId
     * @param removePermissionIdList
     */
    void removeRolePermission(@Param("roleId")Long roleId,@Param("removePermissionIdList")List<Long> removePermissionIdList);

    /**
     * 根据roleId和permissionId查询
     * @param roleId
     * @param permissionId
     */
    RolePermission findByRoleIdAndPermissionId(@Param("roleId")Long roleId,@Param("permissionId")Long permissionId);

}
