package com.pace2car.springbootdemo.web.controller.usercontroller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pace2car.springbootdemo.dto.GetUserParam;
import com.pace2car.springbootdemo.entity.User;
import com.pace2car.springbootdemo.service.UserService;
import com.pace2car.springbootdemo.shiro.anno.PermissionName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Pace2Car
 * @since 2019-01-09
 */
@Api(value = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LogManager.getLogger("userController");

    @Autowired
    private UserService userService;

    @ApiOperation(value = "查询所有用户", notes = "")
    @GetMapping(value = "/all")
    @RequiresPermissions("user:select")
    @PermissionName("查询用户")
    public List<User> getAll() {
        logger.info("查询所有用户");
        return userService.list();
    }

    @ApiOperation(value = "分页条件查询用户", notes = "根据条件分页查询用户")
    @GetMapping(value = "/page")
    @RequiresPermissions("user:select")
    @PermissionName("查询用户")
    public IPage<User> getAllByPage(Integer pageNum, Integer pageSize, User user) {
        logger.info("根据条件分页查询用户");
        return userService.page(new Page<>(pageNum, pageSize), new QueryWrapper<>(user));
    }

    @ApiOperation(value = "根据ID查询用户", notes = "根据ID查询用户")
    @GetMapping(value = "/id")
    @RequiresPermissions("user:select")
    @PermissionName("查询用户")
    public List<User> getAllById(Integer id) {
        logger.info("根据ID查询用户");
        return userService.list(new QueryWrapper<User>().eq("id", id));
    }

    @ApiOperation(value = "联表查询用户", notes = "测试联表查询（常规Mybatis实现）")
    @GetMapping(value = "/complex")
    @RequiresPermissions("user:select")
    @PermissionName("查询用户")
    @ResponseBody
    public List<com.pace2car.springbootdemo.web.vo.User> getComplexUser(GetUserParam param) {
        logger.info("根据复杂条件查询用户");
        long start = System.currentTimeMillis();
        List<com.pace2car.springbootdemo.web.vo.User> userList = userService.getComplexUser(param);
        logger.info(System.currentTimeMillis() - start + "ms");
        return userList;
    }

    @ApiOperation(value = "验证权限AOP", notes = "测试联表查询（常规Mybatis实现）")
    @GetMapping(value = "/verify")
    @PermissionName("查询用户")
    @ResponseBody
    public List<com.pace2car.springbootdemo.web.vo.User> getUserByVerify(GetUserParam param) {
        logger.info("根据复杂条件查询用户");
        long start = System.currentTimeMillis();
        List<com.pace2car.springbootdemo.web.vo.User> userList = userService.getComplexUser(param);
        logger.info(System.currentTimeMillis() - start + "ms");
        return userList;
    }

}
