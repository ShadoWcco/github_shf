package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * Date : 2022/5/19
 * Author : cc
 * Description :
 */
public interface HouseUserMapper extends BaseMapper<HouseUser> {

    List<HouseUser> findHouseUserListByHouseId(Long houseId);

}
