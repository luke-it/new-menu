package com.luke.health.dao;

import com.github.pagehelper.Page;
import com.luke.health.pojo.CheckGroup;
import com.luke.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealDao {

/*    void add(Setmeal setmeal);

    void addSetmealIdAndCheckGroupId(@Param(value = "setemalId")Integer setemalId, @Param(value = "checkgroupId")Integer checkgroupId);*/
void add(Setmeal setmeal);

    void addSetmealAndCheckGroup(Map<String, Object> params);

    Page<Setmeal> findPage(String queryString);
}
