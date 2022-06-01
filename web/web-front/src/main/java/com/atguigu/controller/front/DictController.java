package com.atguigu.controller.front;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Date : 2022/5/18
 * Author : cc
 * Description :
 */
@RestController
@RequestMapping("/dict")
public class DictController extends BaseController {

    @Reference
    private DictService dictService;

    /**
     * 根据父节点的dictCode获取子节点的数据列表
     * @param dictCode
     * @return
     */
    @GetMapping("/findListByDictCode/{dictCode}")
    @ResponseBody
    public Result<List<Dict>> findDictListByDictCode(@PathVariable("dictCode") String dictCode){

        //调用业务层方法根据父节点id获取子节点数据
        List<Dict> list = dictService.findDictListByParentDictCode(dictCode);

        //将子节点数据封装到result中返回
        return Result.ok(list);

    }

    /**
     * 根据父节点的id获取子节点的数据列表
     * @param parentId
     * @return
     */
    @GetMapping("/findListByParentId/{parentId}")
    @ResponseBody
    public Result findDictListByParentId(@PathVariable("parentId") Long parentId){

        //调用业务层根据父节点的dictCode获取子节点的数据
        List<Dict> dictList = dictService.findDictListByParentId(parentId);

        //封装响应数据进行返回
        return Result.ok(dictList);

    }

}
