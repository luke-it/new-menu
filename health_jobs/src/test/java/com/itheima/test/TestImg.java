package com.itheima.test;

import com.luke.health.constant.RedisMessageConstant;
import com.luke.health.utils.QiniuUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;

import javax.ws.rs.core.Context;
import java.util.Iterator;
import java.util.Set;

@ContextConfiguration(locations = "classpath:applicationContext-redis.xml")
@RunWith(value = SpringJUnit4ClassRunner.class)
public class TestImg {

    @Autowired
    JedisPool jedisPool;

    @Test
    public void test(){
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisMessageConstant.SETMEAL_PIC_RESOURCE,RedisMessageConstant.SETMEAL_PIC_DB_DBRESOURCE);
        Iterator<String> iterator = sdiff.iterator();
        while (iterator.hasNext()){
            String img = iterator.next();
            System.out.println("要删除的图片"+img);
            QiniuUtils.deleteFileFromQiniu(img);
            jedisPool.getResource().srem(RedisMessageConstant.SETMEAL_PIC_DB_DBRESOURCE,img);
        }
    }
}
