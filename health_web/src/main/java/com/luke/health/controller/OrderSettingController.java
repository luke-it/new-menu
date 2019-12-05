package com.luke.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.luke.health.constant.MessageConstant;
import com.luke.health.entity.Result;
import com.luke.health.pojo.OrderSetting;
import com.luke.health.service.OrderSettingService;
import com.luke.health.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    OrderSettingService orderSettingService;

//    批量导入
    @RequestMapping("/upload")
    public Result update(MultipartFile excelFile){
        try {
            // 读取报表
            List<String[]> list = POIUtils.readExcel(excelFile);
            List<OrderSetting> orderSettingList = new ArrayList<>();
            // 将list转换成oderSettingList
            for (String[] strings : list) {
                OrderSetting orderSetting = new OrderSetting(new Date(strings[0]), Integer.parseInt(strings [1]));
                orderSettingList.add(orderSetting);
            }
            // 批量保存
            orderSettingService.addList(orderSettingList);
            return  new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }

    }

}
