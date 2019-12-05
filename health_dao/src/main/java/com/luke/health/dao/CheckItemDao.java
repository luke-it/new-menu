package com.luke.health.dao;

import com.luke.health.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {

//新增检查项
    void add(CheckItem checkItem);
//分页查询检查项
    List<CheckItem> findPage(String queryString);

    Long findCheckGroupAndCheckItemCountByCheckItemId(Integer id);

    void delete(Integer id);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();
}
