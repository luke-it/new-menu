package com.luke.health.service;

import com.luke.health.entity.PageResult;
import com.luke.health.pojo.CheckGroup;
import com.luke.health.pojo.Menu;

import java.util.List;

public interface MenuService {

    void add(Menu menu);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    Menu findById(Integer id);

    void update(Menu menu);

    void delete(Integer id);
}
