package com.powernode.manager;

import com.powernode.model.TUser;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class RedisManager {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Object getValue(String key) {
        // redis 数据类型
        // string      list     hash      set       zset
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public <T> Object setValue(String key, Collection<T> data) {
        Long count = 0L;
        for (T item: data) {
            redisTemplate.opsForList().leftPushAll(key, item);
            count ++;
        }
        return count;
    }
}


