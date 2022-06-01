package com.atguigu.controller.front;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.UserInfo;
import com.atguigu.entity.bo.LoginBo;
import com.atguigu.entity.bo.RegisterBo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Date : 2022/5/25
 * Author : cc
 * Description :
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {

    @Reference
    private UserInfoService userInfoService;

    @RequestMapping("/sendCode/{phone}")
    public Result senCode(@PathVariable("phone") String phone, HttpSession session){

        //模拟发送短信
        String code = "1111";

        //存到会话域
        session.setAttribute("CODE",code);

        return Result.ok();

    }

    /**
     * 注册功能
     * @param registerBo
     * @param session
     * @return
     */
    @RequestMapping("/register")
    public Result register(@RequestBody RegisterBo registerBo, HttpSession session){

        //1.校验验证码是否正确
        //从会话域中取出验证码
        String sessionCode = (String) session.getAttribute("CODE");

        //校验
        if (!registerBo.getCode().equalsIgnoreCase(sessionCode)) {

            return Result.build(null, ResultCodeEnum.CODE_ERROR);

        }

        //2.校验手机号是否存在
        UserInfo userInfo = userInfoService.getByPhone(registerBo.getPhone());

        if (userInfo != null) {

            //手机号存在
            return Result.build(null,ResultCodeEnum.PHONE_REGISTER_ERROR);

        }

        //对密码进行加密
        String encryptPassword = MD5.encrypt(registerBo.getPassword());

        //调用业务层把数据保存起来
        userInfo = new UserInfo();

        BeanUtils.copyProperties(registerBo,userInfo);

        userInfo.setStatus(1);

        userInfo.setPassword(encryptPassword);

        userInfoService.insert(userInfo);

        return Result.ok();

    }

    @RequestMapping("/login")
    public Result login(@RequestBody LoginBo loginBo,HttpSession session){

        //根据手机号查找用户,判断手机号是否正确
        UserInfo userInfo = userInfoService.getByPhone(loginBo.getPhone());

        if (userInfo == null) {

            //说明手机号错误
            return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);

        }

        //判断手机号是否被锁定
        if (userInfo.getStatus() == 0){

            //说明用户被锁定
            return Result.build(null,ResultCodeEnum.ACCOUNT_LOCK_ERROR);

        }

        //判断密码是否正确
        if (!userInfo.getPassword().equals(MD5.encrypt(loginBo.getPassword()))) {

            //说明密码错误
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);

        }

        //到这说明用户登录信息正确,存进session中
        session.setAttribute("USER",userInfo);

        //将当前用户的登录信息响应给前端,前端回显
        Map responseMapping = new HashMap<>();

        responseMapping.put("nickName",userInfo.getNickName());
        responseMapping.put("phone",userInfo.getPhone());

        return Result.ok(userInfo);

    }

    @RequestMapping("/logout")
    public Result logout(HttpSession session){

        //直接销毁session
        session.invalidate();

        return Result.ok();

    }

}
