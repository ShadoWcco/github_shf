package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * Date : 2022/5/25
 * Author : cc
 * Description :
 */
public interface UserFollowMapper extends BaseMapper<UserFollow> {

    /**
     * 根据
     * @param userId
     * @param houseId
     * @return
     */
    UserFollow findByUserIdAndHouseId(@Param("userId") Long userId,@Param("houseId") Long houseId);

    /**
     * 分页查询用户的关注列表
     * @param userId
     * @return
     */
    Page<UserFollowVo> findListPage(@Param("userId") Long userId);

}
