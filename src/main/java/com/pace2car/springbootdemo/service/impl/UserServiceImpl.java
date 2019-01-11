package com.pace2car.springbootdemo.service.impl;

import com.github.pagehelper.PageHelper;
import com.pace2car.springbootdemo.dto.GetUserParam;
import com.pace2car.springbootdemo.entity.User;
import com.pace2car.springbootdemo.mapper.UserMapper;
import com.pace2car.springbootdemo.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Pace2Car
 * @since 2019-01-09
 */
@Service
@CacheConfig(cacheNames = "userCache")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private Logger logger = LogManager.getLogger("userServiceImpl");

    @Resource
    private UserMapper userMapper;

    @Override
    @Cacheable(keyGenerator = "wiselyKeyGenerator")
    public List<com.pace2car.springbootdemo.web.vo.User> getComplexUser(GetUserParam param) {
        List<com.pace2car.springbootdemo.web.vo.User> userList;
        if (param.getPageNum() != null && param.getPageSize() != null) {
            PageHelper.startPage(param.getPageNum(), param.getPageSize());
            userList = userMapper.selectComplexUser(param);
        } else {
            PageHelper.startPage(1, 10);
            userList = userMapper.selectComplexUser(param);
        }
        return userList;
    }
}
