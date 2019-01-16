package com.pace2car.springbootdemo.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author Pace2Car
 * @date 2019/1/14 15:09
 */
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {

    private static final int EXPIRE_TIME = 1800;
    private static Logger logger = LogManager.getLogger("redisSessionDAO");
    private static String prefix = "shiro-session:";
    @Resource
    private RedisTemplate<String, Object> sessionRedisTemplate;

    /**
     * 创建session，保存到数据库
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        logger.info("创建session:{}", session.getId());
        sessionRedisTemplate.opsForValue().set(prefix + sessionId.toString(), session);
        return sessionId;
    }

    /**
     * 获取session
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.info("获取session:{}", sessionId);
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = super.doReadSession(sessionId);
        if (session == null) {
            session = (Session) sessionRedisTemplate.opsForValue().get(prefix + sessionId.toString());
        }
        return session;
    }

    /**
     * 更新session的最后一次访问时间
     * @param session
     */
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        logger.info("更新session:{}", session.getId());
        String key = prefix + session.getId().toString();
        if (!sessionRedisTemplate.hasKey(key)) {
            sessionRedisTemplate.opsForValue().set(key, session);
        }
        sessionRedisTemplate.expire(key, EXPIRE_TIME, TimeUnit.SECONDS);
    }

    /**
     * 删除session
     * @param session
     */
    @Override
    protected void doDelete(Session session) {
        logger.info("删除session:{}", session.getId());
        super.doDelete(session);
        sessionRedisTemplate.delete(prefix + session.getId().toString());
    }
}
