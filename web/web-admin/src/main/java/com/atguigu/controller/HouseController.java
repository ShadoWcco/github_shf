package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.en.HouseStatus;
import com.atguigu.entity.House;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Date : 2022/5/19
 * Author : cc
 * Description :
 */
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseUserService houseUserService;

    private static final String PAGE_INDEX = "house/index";
    private static final String PAGE_CREATE = "house/create";
    private static final String PAGE_EDIT = "house/edit";
    private static final String LIST_ACTION = "redirect:/house";
    private static final String PAGE_SHOW = "house/show";

    @RequestMapping
    public String index(@RequestParam Map<String,Object> filters, Model model){

        //获取房源的分页信息
        PageInfo<House> page = houseService.findPage(filters);

        //存储对象
        model.addAttribute("filters",filters);

        model.addAttribute("page",page);

        //获取所有的字典列表,并存储到请求域中
        saveAllDictToRequestScope(model);

        return PAGE_INDEX;

    }

    /**
     * 获取所有字典列表,并存储到请求域中
     * @param model
     */
    public void saveAllDictToRequestScope(Model model){

        //查询小区列表,并存储到请求域
        model.addAttribute("communityList",communityService.findAll());

        //查询房屋类型列表
        model.addAttribute("houseTypeList",dictService.findDictListByParentDictCode("houseType"));

        //查询楼层列表
        model.addAttribute("floorList",dictService.findDictListByParentDictCode("floor"));

        //查询建筑结构列表
        model.addAttribute("buildStructureList",dictService.findDictListByParentDictCode("buildStructure"));

        //查询朝向列表
        model.addAttribute("directionList",dictService.findDictListByParentDictCode("direction"));

        //查询装修情况列表
        model.addAttribute("decorationList",dictService.findDictListByParentDictCode("decoration"));

        //查询房屋用途列表
        model.addAttribute("houseUseList",dictService.findDictListByParentDictCode("houseUse"));

    }

    @RequestMapping("/create")
    public String create(Model model){

        saveAllDictToRequestScope(model);

        return PAGE_CREATE;

    }

    @PostMapping("/save")
    public String save(Model model,House house){

        house.setStatus(HouseStatus.UNPUBLISHED.code);

        houseService.insert(house);

        return successPage(model,"添加房源信息成功");

    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id")Long id, Model model){

        House house = houseService.getById(id);

        model.addAttribute("house",house);

        return PAGE_EDIT;

    }

    @PostMapping("/update")
    public String update(Model model,House house){

        houseService.update(house);

        return successPage(model,"修改房源信息成功");

    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){

        houseService.delete(id);

        return LIST_ACTION;

    }

    @RequestMapping("/publish/{id}/{status}")
    public String publish(@PathVariable("id") Long id,@PathVariable("status") Integer status){

        houseService.publish(id, status);

        return LIST_ACTION;

    }

    @RequestMapping("/{id}")
    public String detail(@PathVariable("id") Long id,Model model){

        //1. 查询房源详情
        House house = houseService.getById(id);
        model.addAttribute("house",house);
        //2. 查询小区详情
        model.addAttribute("community",communityService.getById(house.getCommunityId()));
        //3. 查询房源图片列表
        model.addAttribute("houseImage1List",houseImageService.findHouseImageList(id,1));
        //4. 查询房产图片列表
        model.addAttribute("houseImage2List",houseImageService.findHouseImageList(id,2));
        //5. 查询经纪人列表
        model.addAttribute("houseBrokerList",houseBrokerService.findHouseBrokerListByHouseId(id));
        //6. 查询房东列表
        model.addAttribute("houseUserList",houseUserService.findHouseUserListByHouseId(id));

        return PAGE_SHOW;

    }


}
