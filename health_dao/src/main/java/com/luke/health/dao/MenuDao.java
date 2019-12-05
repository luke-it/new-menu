package com.luke.health.dao;

import com.github.pagehelper.Page;
import com.luke.health.pojo.CheckGroup;
import com.luke.health.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao {

    void add(Menu menu);

    List<Menu> findPage(String queryString);

    Menu findById(Integer id);

    void update(Menu menu);

    void delete(Integer id);
}
