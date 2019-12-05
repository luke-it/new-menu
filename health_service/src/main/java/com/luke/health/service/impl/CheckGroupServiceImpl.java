package com.luke.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luke.health.dao.CheckGroupDao;
import com.luke.health.dao.CheckItemDao;
import com.luke.health.entity.PageResult;
import com.luke.health.pojo.CheckGroup;
import com.luke.health.pojo.CheckItem;
import com.luke.health.service.CheckGroupService;
import com.luke.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    CheckGroupDao checkGroupDao;


    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
//        传递检查项多个id,和检查组的id,向检查组合检查项的中间表中,存放数据
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

//   分页查询
    @Override
    public PageResult findPage(String queryString, Integer currentPage, Integer pageSize) {
        // 初始化页面
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

//  查询检查组主键id
    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

//    更新检查组(先删除中间表,再把数据存入中间表)
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
//        更新检查组,
        checkGroupDao.edit(checkGroup);
//        删除中间表
        checkGroupDao.deleteCheckGroupAndCheckItemCheckGroupId(checkGroup.getId());
//        响中间表中存入数据
        this.setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }


    //    传递检查项多个id,和检查组的id,向检查组合检查项的中间表中,存放数据
    private void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds) {
        if (checkitemIds !=null && checkitemIds.length >0){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupAndCheckItem(checkGroupId,checkitemId);
            }

        }
    }

}
