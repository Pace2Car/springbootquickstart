package com.pace2car.springbootdemo.shiro.service;

import com.pace2car.springbootdemo.shiro.entity.URole;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

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
