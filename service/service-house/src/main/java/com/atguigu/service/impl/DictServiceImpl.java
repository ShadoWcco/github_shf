package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.entity.Dict;
import com.atguigu.mapper.DictMapper;
import com.atguigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Date : 2022/5/18
 * Author : cc
 * Description :
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public List<Map<String, Object>> findZnodes(Long parentId) {

        //查询所有子节点
        List<Dict> dictList = dictMapper.findListByParentId(parentId);

        //遍历出每一个子节点,转换成ztree需要的格式
        List<Map<String,Object>> znodes = dictList.stream().map(dict -> {
            //转换成ztree需要的格式
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",dict.getId());
            map.put("name",dict.getName());
            map.put("isParent",dictMapper.countIsParent(dict.getId()) > 0);
            return map;
        }).collect(Collectors.toList());

        return znodes;
    }

    @Override
    public List<Dict> findDictListByParentDictCode(String parentDictCode) {

        Jedis jedis = null;
        try {
            //从redis中查找是否存有当前父节点的字节点列表
            jedis = jedisPool.getResource();

            String value = jedis.get("shf:dict:parentDictCode:" + parentDictCode);

            if (!StringUtils.isEmpty(value)) {

                //redis中有数据,则将数据转换成list并返回
                return JSON.parseArray(value, Dict.class);

            }

            //redis中没有数据
            List<Dict> dictList = dictMapper.findDictListByParentDictCode(parentDictCode);

            //将DictList转成JSON并存储到redis中
            if (!CollectionUtils.isEmpty(dictList)) {

                jedis.set("shf:dict:parentDictCode:" + parentDictCode, JSON.toJSONString(dictList));

            }

            return dictList;

        } finally {

            //关闭jedis
            if (jedis != null) {
                jedis.close();
            }

            //断开连接
            if (jedis.isConnected()){
                jedis.disconnect();
            }

        }

    }

    @Override
    public List<Dict> findDictListByParentId(Long parentId) {
        Jedis jedis = null;
        try {
            //从redis中查找是否存有当前父节点的字节点列表
            jedis = jedisPool.getResource();

            String value = jedis.get("shf:dict:parentId:" + parentId);

            if (!StringUtils.isEmpty(value)) {

                //redis中有数据,则将数据转换成list并返回
                return JSON.parseArray(value, Dict.class);

            }

            //redis中没有数据
            List<Dict> dictList = dictMapper.findListByParentId(parentId);

            //将DictList转成JSON并存储到redis中
            if (!CollectionUtils.isEmpty(dictList)) {

                jedis.set("shf:dict:parentId:" + parentId, JSON.toJSONString(dictList));

            }

            return dictList;

        } finally {

            //关闭jedis
            if (jedis != null) {
                jedis.close();
            }

            //断开连接
            if (jedis.isConnected()){
                jedis.disconnect();
            }

        }
    }

}
