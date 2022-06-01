package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * Date : 2022/5/19
 * Author : cc
 * Description :
 */
public interface HouseUserService extends BaseService<HouseUser> {

    List<HouseUser> findHouseUserListByHouseId(Long houseId);

}
