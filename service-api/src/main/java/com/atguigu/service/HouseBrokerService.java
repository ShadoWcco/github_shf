package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * Date : 2022/5/19
 * Author : cc
 * Description :
 */
public interface HouseBrokerService extends BaseService<HouseBroker> {

    List<HouseBroker> findHouseBrokerListByHouseId(Long houseId);

}
