package com.pace2car.springbootdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pace2car.springbootdemo.entity.Address;
import com.pace2car.springbootdemo.mapper.AddressMapper;
import com.pace2car.springbootdemo.service.AddressService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Pace2Car
 * @since 2019-01-09
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

}
