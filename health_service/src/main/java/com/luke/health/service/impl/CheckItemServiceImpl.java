package com.luke.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luke.health.dao.CheckItemDao;
import com.luke.health.entity.PageResult;
import com.luke.health.entity.Result;
import com.luke.health.pojo.CheckItem;
import com.luke.health.service.CheckItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    CheckItemDao checkItemDao;
//新增
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

//    分页查询
    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        // 初始化当前页和记录数
        PageHelper.startPage(currentPage,pageSize);
        // 查询返回的怕个对象
        List<CheckItem> list = checkItemDao.findPage(queryString);
        PageInfo<CheckItem> pageInfo = new PageInfo<>(list);
        //封装返回结果PageResult
        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }

//    检查项如果在中间表中有关联,不允许删除
    @Override
    public void delete(Integer id) {
        //删除之前判断,当前检查项在中间表中是否存在记录
        Long count = checkItemDao.findCheckGroupAndCheckItemCountByCheckItemId(id);
        if(count >0){
            throw new RuntimeException("当前检查项在检查组和检查项的中间表中存在数据，不能删除");
        }
        checkItemDao.delete(id);
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {

        return checkItemDao.findAll();
    }

}
