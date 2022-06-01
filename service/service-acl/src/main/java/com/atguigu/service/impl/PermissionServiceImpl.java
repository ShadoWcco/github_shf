package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseController;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Permission;
import com.atguigu.entity.RolePermission;
import com.atguigu.helper.PermissionHelper;
import com.atguigu.mapper.PermissionMapper;
import com.atguigu.mapper.RolePermissionMapper;
import com.atguigu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Date : 2022/5/26
 * Author : cc
 * Description :
 */
@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    protected BaseMapper<Permission> getEntityMapper() {
        return permissionMapper;
    }

    @Override
    public List<Map<String, Object>> findPermissionByRoleId(Long roleId) {

        //获取全部权限列表
        List<Permission> allPermissionList = permissionMapper.findAll();

        //根据roleId查询所有已分配的权限id列表
        List<Long> permissionIdList = rolePermissionMapper.findPermissionIdListByRoleId(roleId);

        //构建zTree数据
        List<Map<String, Object>> zNodes = new ArrayList<>();

        for (Permission permission : allPermissionList) {

            Map<String, Object> zNode = new HashMap<>();

            zNode.put("id",permission.getId());

            zNode.put("pId",permission.getParentId());

            zNode.put("name",permission.getName());

            if (permissionIdList.contains(permission.getId())) {

                zNode.put("checked",true);

            }

            zNodes.add(zNode);

        }

        return zNodes;
    }

    @Override
    public void saveRolePermission(Long roleId, List<Long> permissionIdList) {

        //查询当前角色的所有permissionId
        List<Long> rolePermissionIdList = rolePermissionMapper.findPermissionIdListByRoleId(roleId);

        //找出要移除的permissionId
        List<Long> removePermissionIdList = rolePermissionIdList.stream().filter(item -> !permissionIdList.contains(item)).collect(Collectors.toList());

        //删除角色权限
        if (removePermissionIdList != null && removePermissionIdList.size() > 0){

            rolePermissionMapper.removeRolePermission(roleId, removePermissionIdList);

        }

        //给角色添加权限
        for (Long permissionId : permissionIdList) {

            //根据roleId和permissionId查询角色权限信息
            RolePermission rolePermission = rolePermissionMapper.findByRoleIdAndPermissionId(roleId, permissionId);

            //判断当前roleId和permissionId是否存在关系
            if (rolePermission == null) {

                rolePermission = new RolePermission();

                rolePermission.setPermissionId(permissionId);

                rolePermission.setRoleId(roleId);

                rolePermissionMapper.insert(rolePermission);

            }else{

                if (rolePermission.getIsDeleted() == 1){

                    rolePermission.setIsDeleted(0);

                    rolePermissionMapper.update(rolePermission);

                }

            }

        }

    }

    @Override
    public List<Permission> findMenuPermissionByAdminId(Long adminId) {

        List<Permission> permissionList = null;

        if (adminId == 1){

            //说明是超级管理员
            permissionList = permissionMapper.findAll();

        }else{

            //根据Id查询权限
            permissionList = permissionMapper.findPermissionListByAdminId(adminId);

        }

        return PermissionHelper.build(permissionList);
    }

    @Override
    public List<Permission> findAllMenu() {

        List<Permission> permissionList = permissionMapper.findAll();

        return PermissionHelper.build(permissionList);
    }

    @Override
    public List<String> findCodePermissionListByAdminId(Long adminId) {

        if (adminId == 1){

            return permissionMapper.findAllCodePermission();

        }

        return permissionMapper.findCodePermissionListByAdminId(adminId);
    }

}
