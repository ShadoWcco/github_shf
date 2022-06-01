package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Date : 2022/5/28
 * Author : cc
 * Description :
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根据用户名查询用户
        Admin admin = adminService.getByUsername(username);

        if (admin == null) {

            throw new UsernameNotFoundException("用户名不存在");

        }

        //获取用户权限列表
        List<String> codePermissionList = permissionService.findCodePermissionListByAdminId(admin.getId());

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        for (String code : codePermissionList) {

            if (StringUtils.isEmpty(code)){

                continue;

            }

            grantedAuthorityList.add(new SimpleGrantedAuthority(code));

        }

        //校验密码,操作权限目前为空
        return new User(username,admin.getPassword(), grantedAuthorityList);

    }
}
