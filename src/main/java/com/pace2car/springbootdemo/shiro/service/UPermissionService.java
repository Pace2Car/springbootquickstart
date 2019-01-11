package com.pace2car.springbootdemo.shiro.service;

import com.pace2car.springbootdemo.shiro.entity.UPermission;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Pace2Car
 * @since 2019-01-10
 */
public interface UPermissionService extends IService<UPermission> {
    /**
     * 获取用户可访问的全部资源
     * @return
     */
    List<String> selectAllResourcesByUsername(String username);

    /**
     * 获取全部资源
     * @return
     */
    List<String> selectAllResources();
}
