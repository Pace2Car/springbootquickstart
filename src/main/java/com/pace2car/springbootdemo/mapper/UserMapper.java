package com.pace2car.springbootdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pace2car.springbootdemo.dto.GetUserParam;
import com.pace2car.springbootdemo.entity.User;

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
    List<com.pace2car.springbootdemo.web.vo.User> selectComplexUser(GetUserParam param);
}
