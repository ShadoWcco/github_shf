package com.atguigu.controller;

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

    @GetMapping("/findZnodes")
    public Result findZNodes(@RequestParam(value = "id",defaultValue = "0") Long id){

        List<Map<String, Object>> zNodes = dictService.findZnodes(id);

        return Result.ok(zNodes);

    }

    /**
     * 根据父节点的dictCode获取子节点的数据列表
     * @param dictCode
     * @return
     */
    @GetMapping("/findDictListByDictCode/{dictCode}")
    @ResponseBody
    public Result<List<Dict>> findDictListByDictCode(@PathVariable String dictCode){

        List<Dict> list = dictService.findDictListByParentDictCode(dictCode);

        return Result.ok(list);

    }

    /**
     * 根据父节点的id获取子节点的数据列表
     * @param parentId
     * @return
     */
    @GetMapping("/findDictListByParentId/{parentId}")
    @ResponseBody
    public Result findDictListByParentId(@PathVariable("parentId") Long parentId){

        List<Dict> dictList = dictService.findDictListByParentId(parentId);

        return Result.ok(dictList);

    }

}
