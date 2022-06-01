package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Date : 2022/5/18
 * Author : cc
 * Description :
 */
@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    private final static String PAGE_INDEX = "community/index";
    private final static String PAGE_CREATE = "community/create";
    private final static String PAGE_EDIT = "community/edit";
    private final static String LIST_ACTION = "redirect:/community";

    @RequestMapping
    public String index(@RequestParam Map<String,Object> filters, Model model){

        //处理filters中没有areaId和plateId的情况
        if (!filters.containsKey("areaId")){

            filters.put("areaId","");

        }

        if (!filters.containsKey("plateId")){

            filters.put("plateId","");

        }

        //按条件查询当前页小区的分页数据
        PageInfo<Community> page = communityService.findPage(filters);

        //加载北京所有地区
        List<Dict> areaList = dictService.findDictListByParentDictCode("北京");

        //将数据存储到请求域
        model.addAttribute("page",page);

        model.addAttribute("areaList",areaList);

        model.addAttribute("filters",filters);

        return PAGE_INDEX;

    }

    @RequestMapping("/create")
    public String create(Model model){

        List<Dict> areaList = dictService.findDictListByParentDictCode("beijing");

        model.addAttribute("areaList",areaList);

        return PAGE_CREATE;

    }

    @PostMapping("/save")
    public String save(Community community,Model model){

        communityService.insert(community);

        return successPage(model,"添加小区成功");

    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model){

        //查询小区信息
        Community community = communityService.getById(id);

        model.addAttribute("community",community);

        //查询所有beijing的区域
        List<Dict> areaList = dictService.findDictListByParentDictCode("beijing");

        model.addAttribute("areaList",areaList);

        return PAGE_EDIT;

    }

    @PostMapping("/update")
    public String update(Community community,Model model){

        communityService.update(community);

        return successPage(model,"修改小区信息成功");

    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){

        communityService.delete(id);

        return LIST_ACTION;

    }

}
