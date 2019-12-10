package com.pace2car.springbootdemo.shiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pace2car.springbootdemo.shiro.entity.UUser;
import com.pace2car.springbootdemo.shiro.mapper.UUserMapper;
import com.pace2car.springbootdemo.shiro.service.UUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Pace2Car
 * @since 2019-01-10
 */
@Service
public class UUserServiceImpl extends ServiceImpl<UUserMapper, UUser> implements UUserService {

    @Resource
    private UUserMapper uuserMapper;

}
