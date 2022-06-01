package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.Permission;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Date : 2022/5/27
 * Author : cc
 * Description :
 */
@Controller
public class IndexController extends BaseController {

    private static final String PAGE_INDEX = "frame/index";
    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    @GetMapping("/")
    public String index(Model model){

        //获取当前登录的用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        Admin admin = adminService.getByUsername(user.getUsername());

        List<Permission> permissionList =  permissionService.findMenuPermissionByAdminId(admin.getId());

        model.addAttribute("admin",admin);

        model.addAttribute("permissionList",permissionList);

        return PAGE_INDEX;

    }

}
