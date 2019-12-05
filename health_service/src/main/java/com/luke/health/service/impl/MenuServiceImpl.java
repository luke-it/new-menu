package com.luke.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luke.health.dao.CheckGroupDao;
import com.luke.health.dao.MenuDao;
import com.luke.health.entity.PageResult;
import com.luke.health.pojo.CheckGroup;
import com.luke.health.pojo.Menu;
import com.luke.health.service.CheckGroupService;
import com.luke.health.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuDao menuDao;


    @Override
    public void add(Menu menu) {
        menuDao.add(menu);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
//        初始化当前页面和记录数
        PageHelper.startPage(currentPage,pageSize);
        List<Menu> list = menuDao.findPage(queryString);
        PageInfo<Menu> pageInfo = new PageInfo<>(list);
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public Menu findById(Integer id) {
        return menuDao.findById(id);

    }

    @Override
    public void update(Menu menu) {
        menuDao.update(menu);
    }

    @Override
    public void delete(Integer id) {
        menuDao.delete(id);
    }
}
