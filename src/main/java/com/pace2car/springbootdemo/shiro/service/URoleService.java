package com.pace2car.springbootdemo.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pace2car.springbootdemo.shiro.entity.URole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Pace2Car
 * @since 2019-01-10
 */
public interface URoleService extends IService<URole> {
    /**
     * 获取用户的全部角色
     * @param username
     * @return
     */
    List<String> selectAllRolesByUsername(String username);
}
