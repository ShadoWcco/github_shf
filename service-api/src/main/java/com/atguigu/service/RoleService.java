package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/*
 * Date : 2022/5/12
 * Author : cc
 * Description :
 */


public interface RoleService extends BaseService<Role> {

    List<Role> findAll();

    Map<String,List<Role>> findRoleIdByAdminId(Long adminId);

    /**
     * 保存用户角色
     * @param adminId
     * @param roleIdList
     */
    void saveAdminRole(Long adminId,List<Long> roleIdList);

}
