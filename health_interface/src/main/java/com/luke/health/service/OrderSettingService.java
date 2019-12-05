package com.luke.health.service;

import com.luke.health.entity.PageResult;
import com.luke.health.pojo.CheckGroup;
import com.luke.health.pojo.OrderSetting;

import java.util.List;

public interface OrderSettingService {

    void addList(List<OrderSetting> orderSettingList);
}
