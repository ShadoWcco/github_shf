package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Date : 2022/5/19
 * Author : cc
 * Description :
 */
public interface HouseImageService extends BaseService<HouseImage> {

    List<HouseImage> findHouseImageList(@Param("houseId")Long houseId, @Param("type") Integer type);

}
