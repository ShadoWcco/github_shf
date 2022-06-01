package com.atguigu.controller.front;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.*;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.atguigu.result.Result;
import com.atguigu.service.*;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Date : 2022/5/24
 * Author : cc
 * Description :
 */
@RestController
@RequestMapping("/house")
public class HouseController extends BaseController {

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private UserFollowService userFollowService;

    @PostMapping("/list/{pageNum}/{pageSize}")
    public Result findListPage(@RequestBody HouseQueryBo houseQueryBo,@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize){

        //调用业务层方法查询分页数据
        PageInfo<HouseVo> pageInfo = houseService.findListPage(pageNum, pageSize, houseQueryBo);

        return Result.ok(pageInfo);

    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpSession session){

        //获取房源的信息
        House house = houseService.getById(id);

        //获取小区的信息
        Community community = communityService.getById(house.getCommunityId());

        //获取经济人信息列表
        List<HouseBroker> houseBrokerList = houseBrokerService.findHouseBrokerListByHouseId(id);

        //获取房产图片信息
        List<HouseImage> houseImageList = houseImageService.findHouseImageList(id, 1);

        //查询当前用户是否关注该房源
        UserInfo userInfo = (UserInfo) session.getAttribute("USER");

        Boolean isFollow = false;

        if (userInfo != null) {

            UserFollow userFollow = userFollowService.findByUserIdAndHouseId(userInfo.getId(), id);

            if (userFollow != null && userFollow.getIsDeleted() == 0){

                //表示已关注
                isFollow = true;

            }

        }

        Map<String, Object> map = new HashMap<>();

        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImageList);
        map.put("isFollow",false);

        return Result.ok(map);

    }

}
