package com.luke.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.luke.health.dao.OrderSettingDao;
import com.luke.health.pojo.OrderSetting;
import com.luke.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    OrderSettingDao orderSettingDao;


    @Override
    public void addList(List<OrderSetting> orderSettingList) {
//        导入的表格不为空
        if (orderSettingList !=null && orderSettingList.size() > 0){
            for (OrderSetting orderSetting : orderSettingList) {
                orderSettingDao.add(orderSetting);
            }
        }

    }
}
