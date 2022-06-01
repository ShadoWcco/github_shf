package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Date : 2022/5/26
 * Author : cc
 * Description :
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * 根据用户id查找角色id
     * @param adminId
     * @return
     */
    List<Long> findRoleIdByAdminId(Long adminId);

    /**
     * 根据用户id和角色id查询用户是否绑定过该角色
     * @param adminId
     * @param roleId
     * @return
     */
    AdminRole findByAdminIdAndRoleId(@Param("adminId")Long adminId,@Param("roleId")Long roleId);

    /**
     * 移除用户绑定的角色
     * @param adminId
     * @param removeRoleIdList
     */
    void removeAdminRole(@Param("adminId")Long adminId, @Param("roleIds")List<Long> removeRoleIdList);

}
