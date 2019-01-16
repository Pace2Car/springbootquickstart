package com.pace2car.springbootdemo.config;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author Pace2Car
 * @date 2019/1/14 15:24
 */
public class ShiroRedisCacheManager extends AbstractCacheManager {
    @Resource
    private RedisTemplate<String, Object> sessionRedisTemplate;

    @Override
    protected Cache<String, Object> createCache(String name) throws CacheException {
        return new ShiroRedisCache<>(sessionRedisTemplate, name);
    }
}
