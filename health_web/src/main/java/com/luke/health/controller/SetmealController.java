package com.luke.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.luke.health.constant.MessageConstant;
import com.luke.health.constant.RedisMessageConstant;
import com.luke.health.entity.PageResult;
import com.luke.health.entity.QueryPageBean;
import com.luke.health.entity.Result;
import com.luke.health.pojo.CheckGroup;
import com.luke.health.pojo.Setmeal;
import com.luke.health.service.CheckGroupService;
import com.luke.health.service.SetmealService;
import com.luke.health.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    SetmealService setmealService;

    @Autowired
    JedisPool jedisPool;


//   将图片上传到七牛云
    @RequestMapping(value = "/upload")
    public Result upload(@RequestParam(value = "imgFile")MultipartFile imgFile){
        try {
//        获取文件名
            String fileName = imgFile.getOriginalFilename();
//        获取uuid随机生成的文件名
            String uuidNema = UUID.randomUUID().toString();
//        拼接完整的文件名
            fileName = uuidNema+fileName.substring(fileName.lastIndexOf("."));
//        上传七牛云
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
//            将上传到七牛云的图片存到redis
            jedisPool.getResource().sadd(RedisMessageConstant.SETMEAL_PIC_RESOURCE,fileName);
            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

////  保存套餐
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer [] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
            return  new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }

    }

//    分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

        PageResult pageResult =setmealService.findPage(queryPageBean.getQueryString(),queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        return pageResult;

    }


}
