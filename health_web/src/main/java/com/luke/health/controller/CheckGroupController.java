package com.luke.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.luke.health.constant.MessageConstant;
import com.luke.health.entity.PageResult;
import com.luke.health.entity.QueryPageBean;
import com.luke.health.entity.Result;
import com.luke.health.pojo.CheckGroup;
import com.luke.health.pojo.CheckItem;
import com.luke.health.service.CheckGroupService;
import com.luke.health.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    CheckGroupService checkGroupService;
//  新增检查组
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer [] checkitemIds){
        try {
            checkGroupService.add(checkGroup,checkitemIds);
            return  new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
        }

    }

//    分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

        return checkGroupService.findPage(queryPageBean.getQueryString(),queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
    }

//    编辑当前行,根据id查询当前行的数据,回显到当前表单
    @RequestMapping("/findById")
    public  Result findById(Integer id){
        CheckGroup checkGroup = checkGroupService.findById(id);
        if (checkGroup !=null ){
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkGroup);
        }else {
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

//    根据检查组id查询所有检查项
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id){
        List<Integer> list = checkGroupService.findCheckItemIdsByCheckGroupId(id);
        return list;
    }

    //  编辑检查组
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer [] checkitemIds){
        try {
            checkGroupService.edit(checkGroup,checkitemIds);
            return  new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }

    }

//    查找所有检查组
    @RequestMapping(value = "/findAll")
    public Result findAll(){
       List<CheckGroup> list = checkGroupService.findAll();
      if (list !=null && list.size()>0){
          return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
      }else {
          return new Result(true,MessageConstant.QUERY_CHECKGROUP_FAIL);
      }
    }

}
