package com.luke.health.service;

import com.luke.health.entity.PageResult;
import com.luke.health.pojo.CheckGroup;
import com.luke.health.pojo.Setmeal;

import java.util.List;

public interface SetmealService {


    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findPage(String queryString, Integer currentPage, Integer pageSize);
}
