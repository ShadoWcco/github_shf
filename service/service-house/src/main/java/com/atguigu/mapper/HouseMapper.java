package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.House;
import com.atguigu.entity.HouseImage;
import com.atguigu.entity.bo.HouseQueryBo;
import com.atguigu.entity.vo.HouseVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Date : 2022/5/19
 * Author : cc
 * Description :
 */
public interface HouseMapper extends BaseMapper<House> {

    Page<HouseVo> findListPage(HouseQueryBo houseQueryBo);

}
