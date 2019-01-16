package com.pace2car.springbootdemo.web.controller.basecontroller;

import com.pace2car.springbootdemo.service.AsyncService;
import com.pace2car.springbootdemo.shiro.anno.PermissionName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pace2Car
 * @date 2019/1/10 14:41
 */
@Api(value = "异步管理")
@RestController
@RequestMapping("async")
public class AsyncController {

    private Logger logger = LogManager.getLogger("asyncController");

    @Autowired
    private AsyncService asyncService;

    @ApiOperation(value = "异步请求测试", notes = "")
    @RequiresPermissions("async:request")
    @PermissionName("异步请求权限")
    @GetMapping("/sub")
    public String submit(){
        logger.info("start submit");

        // 调用service层的任务
        asyncService.executeAsync();

        logger.info("end submit");

        return "success";
    }

}
