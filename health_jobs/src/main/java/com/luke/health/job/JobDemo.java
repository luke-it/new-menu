package com.luke.health.job;

import com.luke.health.constant.RedisMessageConstant;
import com.luke.health.utils.QiniuUtils;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Set;

//@RestController
public class JobDemo {

    @Autowired
    JedisPool jedisPool;

    public void  clearImg(){
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisMessageConstant.SETMEAL_PIC_DB_DBRESOURCE, RedisMessageConstant.SETMEAL_PIC_RESOURCE);
        Iterator<String> iterator = sdiff.iterator();
        while (iterator.hasNext()){
            String img = iterator.next();
            System.out.println("要删除的图片"+img);
            QiniuUtils.deleteFileFromQiniu(img);
            jedisPool.getResource().srem(RedisMessageConstant.SETMEAL_PIC_DB_DBRESOURCE,img);
        }
    }
}
