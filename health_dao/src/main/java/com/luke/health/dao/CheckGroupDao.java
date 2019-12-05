package com.luke.health.dao;

import com.github.pagehelper.Page;
import com.luke.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);


    void addCheckGroupAndCheckItem(@Param(value = "checkGroupId") Integer checkGroupId, @Param(value = "checkitemId")Integer checkitemId);
//分页查询
    Page<CheckGroup> findPage(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup);


    void deleteCheckGroupAndCheckItemCheckGroupId(Integer id);


    List<CheckGroup> findAll();
}
