package com.pace2car.springbootdemo.shiro.mapper;

import com.pace2car.springbootdemo.shiro.entity.UPermission;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Pace2Car
 * @since 2019-01-10
 */
public interface UPermissionMapper extends BaseMapper<UPermission> {
    /**
     * 获取角色全部资源
     * @param shortName
     * @return
     */
    List<String> selectAllResourcesByRole(String shortName);

    /**
     * 获取全部资源
     * @return
     */
    List<String> selectAllResources();
}
