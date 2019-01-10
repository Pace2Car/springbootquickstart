package com.pace2car.springbootdemo.service;

import com.pace2car.springbootdemo.dto.GetUserParam;
import com.pace2car.springbootdemo.entity.User;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Pace2Car
 * @since 2019-01-09
 */
public interface UserService extends IService<User> {
    List<com.pace2car.springbootdemo.vo.User> getComplexUser(GetUserParam param);
}
