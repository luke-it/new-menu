package com.luke.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.luke.health.constant.RedisMessageConstant;
import com.luke.health.dao.CheckGroupDao;
import com.luke.health.dao.SetmealDao;
import com.luke.health.entity.PageResult;
import com.luke.health.pojo.CheckGroup;
import com.luke.health.pojo.Setmeal;
import com.luke.health.service.CheckGroupService;
import com.luke.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealDao setmealDao;

    @Autowired
        JedisPool jedisPool;
//    保存套餐信息
   /* @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        setSetmealIdAndCheckGroupId(setmeal.getId(),checkgroupIds);
    }

    private void setSetmealIdAndCheckGroupId(Integer setemalId, Integer[] checkgroupIds) {
        if (checkgroupIds !=null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealIdAndCheckGroupId(setemalId,checkgroupId);
            }
        }
    }*/

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //1：保存套餐信息
        setmealDao.add(setmeal);
        //2：遍历检查组的集合，向套餐和检查组的中间表中插入数据
        if(checkgroupIds!=null && checkgroupIds.length>0){
            // 使用套餐id和检查组id，向套餐和检查组的中间表中插入数据
            setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
        }
        // 保存套餐（保存数据库）的代码：向Redis中存放数据，使用集合存储方案，key值为=setmealPicDbResource；value值为图片名称
      jedisPool.getResource().sadd(RedisMessageConstant.SETMEAL_PIC_DB_DBRESOURCE,setmeal.getImg());
    }

    @Override
    public PageResult findPage(String queryString, Integer currentPage, Integer pageSize) {
        // 初始化页面
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal>  page =  setmealDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    // 使用套餐id和检查组id，向套餐和检查组的中间表中插入数据
    private void setSetmealAndCheckGroup(Integer setmealId, Integer[] checkgroupIds) {
        for (Integer checkgroupId : checkgroupIds) {
            Map<String,Object> params = new HashMap<>();
            params.put("setmealId",setmealId);
            params.put("checkgroupId",checkgroupId);
            setmealDao.addSetmealAndCheckGroup(params);
        }

    }
}
