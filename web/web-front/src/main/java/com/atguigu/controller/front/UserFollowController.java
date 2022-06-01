package com.atguigu.controller.front;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.UserInfo;
import com.atguigu.entity.vo.UserFollowVo;
import com.atguigu.result.Result;
import com.atguigu.service.UserFollowService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Date : 2022/5/25
 * Author : cc
 * Description :
 */
@RestController
@RequestMapping("/userFollow")
public class UserFollowController extends BaseController {

    @Reference
    private UserFollowService userFollowService;

    @RequestMapping("/auth/follow/{houseId}")
    public Result addFollow(@PathVariable("houseId")Long houseId, HttpSession session){

        UserInfo userInfo = (UserInfo) session.getAttribute("USER");

        UserFollow userFollow = userFollowService.findByUserIdAndHouseId(userInfo.getId(), houseId);

        //如果用户添加过关注,只需要将isDelete更改为0
        if (userFollow != null) {

            //说明之前关注过
            userFollow.setIsDeleted(0);

            //更新关注
            userFollowService.update(userFollow);

        }else{

            //没有关注过,需要新增一条数据
            userFollow = new UserFollow();

            userFollow.setUserId(userInfo.getId());

            userFollow.setHouseId(houseId);

            userFollowService.insert(userFollow);

        }

        return Result.ok();

    }

    @GetMapping("/auth/list/{pageNum}/{pageSize}")
    public Result findListPage(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize,HttpSession session){

        UserInfo userInfo = (UserInfo) session.getAttribute("USER");

        PageInfo<UserFollowVo> pageInfo = userFollowService.findListPage(pageNum, pageSize, userInfo.getId());

        return Result.ok(pageInfo);

    }

    @RequestMapping("/auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable("id") Long id){

        //创建UserFollow对象
        UserFollow userFollow = new UserFollow();

        userFollow.setId(id);

        userFollow.setIsDeleted(1);

        //调用业务层方法修改
        userFollowService.update(userFollow);

        return Result.ok();

    }


}
