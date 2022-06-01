package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Date : 2022/5/20
 * Author : cc
 * Description :
 */
@Controller
@RequestMapping("/houseUser")
public class HouseUserController extends BaseController {

    @Reference
    private HouseUserService houseUserService;

    private static final String PAGE_CREATE = "houseUser/create";
    private static final String PAGE_EDIT = "houseUser/edit";
    private static final String SHOW_ACTION = "redirect:/house/";

    @RequestMapping("/create")
    public String create(Model model, HouseUser houseUser){

        model.addAttribute("houseUser",houseUser);

        return PAGE_CREATE;

    }

    @PostMapping("/save")
    public String save(Model model,HouseUser houseUser){

        houseUserService.insert(houseUser);

        return successPage(model,"添加房东成功");

    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")Long id,Model model){

        HouseUser houseUser = houseUserService.getById(id);

        model.addAttribute("houseUser",houseUser);

        return PAGE_EDIT;

    }

    @RequestMapping("/update")
    public String update(Model model,HouseUser houseUser){

        houseUserService.update(houseUser);

        return successPage(model,"修改房东信息成功");

    }

    @GetMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable("id") Long id){

        houseUserService.delete(id);

        return SHOW_ACTION + houseId;

    }

}
