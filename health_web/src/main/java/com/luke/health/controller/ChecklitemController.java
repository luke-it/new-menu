package com.luke.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.luke.health.constant.MessageConstant;
import com.luke.health.entity.PageResult;
import com.luke.health.entity.QueryPageBean;
import com.luke.health.entity.Result;
import com.luke.health.pojo.CheckItem;
import com.luke.health.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class ChecklitemController {
    /*体检检查项管理*/

    @Reference
    CheckItemService checkItemService;

//新增检查项
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

// 分页查询检查项
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkItemService.findPage(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }


    //    删除检查项
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            checkItemService.delete(id);
            return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());//
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    //    根据ID查询检查项
    @RequestMapping("/findById")
    public Result findById(Integer id){
           CheckItem checkItem = checkItemService.findById(id);
            if(checkItem!= null){
                return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
            }else {
                return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
            }

    }

    //编辑检查项
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkItemService.edit(checkItem);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

//    查询所有检查项封装到tableData模型中
    @RequestMapping("/findAll")
    public  Result findA(){
       List<CheckItem> list = checkItemService.findAll();
       if(list !=null && list.size() >0){
           return  new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
       }else {
           return  new Result(true,MessageConstant.QUERY_CHECKGROUP_FAIL);
       }
    }
}
