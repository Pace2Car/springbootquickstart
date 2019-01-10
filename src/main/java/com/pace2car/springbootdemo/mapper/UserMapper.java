package com.pace2car.springbootdemo.mapper;

import com.pace2car.springbootdemo.dto.GetUserParam;
import com.pace2car.springbootdemo.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Pace2Car
 * @since 2019-01-09
 */
public interface UserMapper extends BaseMapper<User> {
    List<com.pace2car.springbootdemo.vo.User> selectComplexUser(GetUserParam param);
}
