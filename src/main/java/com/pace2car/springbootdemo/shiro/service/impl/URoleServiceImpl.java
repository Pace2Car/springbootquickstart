package com.pace2car.springbootdemo.shiro.service.impl;

import com.pace2car.springbootdemo.shiro.entity.URole;
import com.pace2car.springbootdemo.shiro.mapper.URoleMapper;
import com.pace2car.springbootdemo.shiro.service.URoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Pace2Car
 * @since 2019-01-10
 */
@Service
public class URoleServiceImpl extends ServiceImpl<URoleMapper, URole> implements URoleService {

    @Resource
    private URoleMapper uRoleMapper;

    @Override
    public List<String> selectAllRolesByUsername(String username) {
        return uRoleMapper.selectAllRolesByUsername(username);
    }
}
