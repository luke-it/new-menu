package com.luke.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.luke.health.constant.MessageConstant;
import com.luke.health.entity.PageResult;
import com.luke.health.entity.QueryPageBean;
import com.luke.health.entity.Result;
import com.luke.health.pojo.CheckGroup;
import com.luke.health.pojo.Menu;
import com.luke.health.service.CheckGroupService;
import com.luke.health.service.MenuService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Reference
    MenuService menuService;
//  新增菜單
    @RequestMapping("/add")
    public Result add(@RequestBody Menu menu){
        try {
                menuService.add(menu);
            return  new Result(true,MessageConstant.ADD_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.ADD_MENU_FAIL);
        }

    }

//分頁查詢
    @RequestMapping("/findPage")
    public  PageResult findPage(@RequestBody QueryPageBean queryPageBean){
      PageResult pageResult =  menuService.findPage(queryPageBean.getCurrentPage(),
              queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return pageResult;
    }

//    查询当前id菜单
    @RequestMapping("findById")
    public  Result findById(Integer id){
        Menu menu =menuService.findById(id);
        if (menu !=null){
            return  new Result(true,MessageConstant.QUERY_MENU_SUCCESS,menu);
        }else {
            return  new Result(true,MessageConstant.QUERY_MENU_FAIL);
        }

    }

//    编辑菜单
    @RequestMapping("update")
    public Result update(@RequestBody Menu menu){

        try {
            menuService.update(menu);
            return new Result(true, MessageConstant.EDIT_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_MENU_FAIL);
        }
    }

//    删除菜单

    @RequestMapping("/delete")
        public Result delete(Integer id){
        try {
            menuService.delete(id);
            return new Result(true,MessageConstant.DELETE_MENU_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_MENU_FAIL);
        }
    }

}
