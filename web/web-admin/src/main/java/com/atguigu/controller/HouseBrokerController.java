package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Date : 2022/5/19
 * Author : cc
 * Description :
 */
@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController extends BaseController {

    @Reference
    private AdminService adminService;

    @Reference
    private HouseBrokerService houseBrokerService;

    private static final String PAGE_CREATE = "houseBroker/create";
    private static final String PAGE_EDIT = "houseBroker/edit";
    private static final String SHOW_ACTION = "redirect:/house/";

    @RequestMapping("/create")
    public String create(Model model, HouseBroker houseBroker){

        saveAdminListToModel(model);

        model.addAttribute("houseBroker",houseBroker);

        return PAGE_CREATE;

    }

    public void saveAdminListToModel(Model model){

        List<Admin> adminList = adminService.findAll();

        model.addAttribute("adminList",adminList);

    }

    @PostMapping("/save")
    public String save(Model model,HouseBroker houseBroker){

        Admin admin = adminService.getById(houseBroker.getBrokerId());

        houseBroker.setBrokerName(admin.getName());

        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());

        houseBrokerService.insert(houseBroker);

        return successPage(model,"添加经纪人成功");

    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model){

        HouseBroker houseBroker = houseBrokerService.getById(id);

        saveAdminListToModel(model);

        model.addAttribute("houseBroker",houseBroker);

        return PAGE_EDIT;

    }

    @PostMapping("/update")
    public String update(Model model,HouseBroker houseBroker){

        //根据id查找经纪人信息
        Admin admin = adminService.getById(houseBroker.getBrokerId());

        //更新经纪人信息

        houseBroker.setBrokerId(admin.getId());

        houseBroker.setBrokerName(admin.getName());

        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());

        houseBrokerService.update(houseBroker);

        return successPage(model,"修改经纪人成功");

    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId")Long houseId,@PathVariable("id")Long id){

        houseBrokerService.delete(id);

        return SHOW_ACTION + houseId;

    }

}
