package com.atguigu.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.AdminRole;
import com.atguigu.entity.Role;
import com.atguigu.mapper.AdminRoleMapper;
import com.atguigu.mapper.RoleMapper;
import com.atguigu.service.AdminService;
import com.atguigu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Date : 2022/5/12
 * Author : cc
 * Description :
 */
@Service(interfaceClass = RoleService.class)
@Transactional(propagation = Propagation.REQUIRED)
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Override
    protected BaseMapper<Role> getEntityMapper() {
        return roleMapper;
    }

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    @Override
    public Map<String, List<Role>> findRoleIdByAdminId(Long adminId) {

        //查询所有用户
        List<Role> allRoleList = roleMapper.findAll();

        //根据用户id查询所有已分配角色的id列表
        List<Long> assignRoleIdList = adminRoleMapper.findRoleIdByAdminId(adminId);

        ArrayList<Role> assignRoleList = new ArrayList<>();

        ArrayList<Role> unAssignRoleList = new ArrayList<>();

        for (Role role : allRoleList) {

            if (assignRoleIdList.contains(role.getId())){

                //已分配
                assignRoleList.add(role);

            }else {

                //未分配
                unAssignRoleList.add(role);

            }

        }

        Map<String, List<Role>> map = new HashMap<>();

        map.put("assignRoleList",assignRoleList);
        map.put("unAssignRoleList",unAssignRoleList);

        return map;
    }

    @Override
    public void saveAdminRole(Long adminId, List<Long> roleIdList) {

        //查询当前用户的所有角色id
        List<Long> adminRoleIdList = adminRoleMapper.findRoleIdByAdminId(adminId);

        //找出要移除的角色id
        List<Long> removeRoleIdList = adminRoleIdList.stream()
                .filter(item -> !roleIdList.contains(item))
                .collect(Collectors.toList());

        //移除角色
        if (removeRoleIdList != null && removeRoleIdList.size() > 0) {

             adminRoleMapper.removeAdminRole(adminId,removeRoleIdList);

        }

        //添加角色权限
        for (Long roleId : roleIdList) {

            AdminRole adminRole = adminRoleMapper.findByAdminIdAndRoleId(adminId, roleId);

            if (adminRole == null) {

                adminRole = new AdminRole();

                adminRole.setAdminId(adminId);

                adminRole.setRoleId(roleId);

                adminRoleMapper.insert(adminRole);

            }else{

                if (adminRole.getIsDeleted() == 1){

                    adminRole.setIsDeleted(0);

                    adminRoleMapper.update(adminRole);

                }

            }

        }

    }


}
