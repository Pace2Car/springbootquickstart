package com.pace2car.springbootdemo.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pace2car.springbootdemo.shiro.entity.URole;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Pace2Car
 * @since 2019-01-10
 */
public interface URoleMapper extends BaseMapper<URole> {

    /**
     * 获取用户的全部角色
     * @param username
     * @return
     */
    List<String> selectAllRolesByUsername(String username);
}
