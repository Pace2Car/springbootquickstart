package com.pace2car.springbootdemo;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.pace2car.springbootdemo.entity.User;
import com.pace2car.springbootdemo.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootdemoApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(new EntityWrapper<User>());
        Assert.assertEquals(3, userList.size());
        userList.forEach(System.out::println);
    }

}

