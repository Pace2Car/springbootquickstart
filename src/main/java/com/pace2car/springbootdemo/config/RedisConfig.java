package com.pace2car.springbootdemo.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Redis缓存配置类
 *
 * @author Pace2Car
 * @date 2019/1/11 15:21
 */
@Configuration
@EnableCaching
public class RedisConfig {
    /**
     * 定义缓存数据 key 生成策略的bean
     * key值为：包名+类名+方法名+所有参数
     *
     * @return
     */
    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder()
                    .append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            sb.append(")");
            return sb.toString();
        };

    }


    /**
     * 要启用spring缓存支持,需创建一个 CacheManager的 bean，CacheManager 接口有很多实现，这里Redis 的集成，用 RedisCacheManager这个实现类
     * Redis 不是应用的共享内存，它只是一个内存服务器，就像 MySql 似的，
     * 我们需要将应用连接到它并使用某种“语言”进行交互，因此我们还需要一个连接工厂以及一个 Spring 和 Redis 对话要用的 RedisTemplate，
     * 这些都是 Redis 缓存所必需的配置，把它们都放在自定义的 CachingConfigurerSupport 中
     *
     * @param factory
     * @return
     */
    @Bean(name = "redisCacheManager")
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(600))
                .disableCachingNullValues();

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }

    /**
     * 1.项目启动时此方法先被注册成bean被spring管理
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);

        template.setValueSerializer(serializer);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

}
