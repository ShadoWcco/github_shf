package com.atguigu.mapper;

import com.atguigu.entity.Dict;

import java.util.List;

/**
 * Date : 2022/5/18
 * Author : cc
 * Description :
 */
public interface DictMapper {

    List<Dict> findListByParentId(Long prentId);

    Integer countIsParent(Long id);

    List<Dict> findDictListByParentDictCode(String parentDictCode);

}
