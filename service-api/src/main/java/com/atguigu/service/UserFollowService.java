package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

/**
 * Date : 2022/5/25
 * Author : cc
 * Description :
 */
public interface UserFollowService extends BaseService<UserFollow> {

    UserFollow findByUserIdAndHouseId(Long userId,Long houseId);

    PageInfo<UserFollowVo> findListPage(int pageNum,int pageSize,Long userId);

}
