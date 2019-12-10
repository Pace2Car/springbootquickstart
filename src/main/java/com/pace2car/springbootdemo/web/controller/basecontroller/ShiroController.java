package com.pace2car.springbootdemo.web.controller.basecontroller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pace2car.springbootdemo.shiro.anno.PermissionName;
import com.pace2car.springbootdemo.shiro.entity.UPermission;
import com.pace2car.springbootdemo.shiro.entity.UUser;
import com.pace2car.springbootdemo.shiro.service.UPermissionService;
import com.pace2car.springbootdemo.shiro.service.UUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 认证授权前端控制器
 * </p>
 *
 * @author Pace2Car
 * @since 2019-01-10
 */
@Api(value = "权限管理")
@Controller
public class ShiroController {

    private Logger logger = LogManager.getLogger("userController");

    private static final String UNKNOWN_ACCOUNT = "org.apache.shiro.authc.UnknownAccountException";

    private static final String INCORRECT_CREDENTIALS = "org.apache.shiro.authc.IncorrectCredentialsException";

    @Autowired
    private UUserService uuserService;

    @Autowired
    private UPermissionService uPermissionService;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @RequestMapping(value = "/login")
    public String login(HttpSession session, HttpServletRequest request) {
        // 获取登录失败异常信息
        String failureMessage = (String) request.getAttribute("shiroLoginFailure");
        // 根据返回的异常信息判断返回的异常信息
        if (failureMessage == null) {
            logger.info("请先登录");
        } else if (UNKNOWN_ACCOUNT.equals(failureMessage)) {
            logger.info("账户不存在");
        } else if (INCORRECT_CREDENTIALS.equals(failureMessage)) {
            logger.info("密码错误");
        }
        return "login";
    }

    @RequestMapping("/logOut")
    public String logOut() {
        SecurityUtils.getSubject().logout();
        logger.info("用户登出：{}", SecurityUtils.getSubject());
        return "login";
    }

    @RequestMapping(value = "/notLogin")
    public String notLogin(HttpSession session, HttpServletRequest request) {
        return "login";
    }

    @ApiOperation(value = "查询所有用户", notes = "登录成功的跳转页面")
    @GetMapping(value = "/success")
    @ResponseBody
    public List<UUser> getAll() {
        logger.info("查询所有用户");
        return uuserService.list();
    }

    /**
     * 未登录，可以访问的页面
     *
     * @return
     */
    @RequestMapping("/index")
    public String list() {
        return "index";
    }


    /**
     * 未授权
     *
     * @return
     */
    @GetMapping("/403")
    public String notRole() {
        return "403";
    }

    /**
     * 将系统中所有的权限表达式装入数据库
     */
    @ApiOperation(value = "刷新权限", notes = "")
    @GetMapping("/reloadPermission")
    @RequiresPermissions("permission:insert")
    @PermissionName("刷新权限")
    @ResponseBody
    public List<String> reloadPermission() {
        //已有的资源
        List<String> resourcesList = uPermissionService.selectAllResources();
        // 获取所有Controller中带有@RequestMapping标签的方法
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        Collection<HandlerMethod> methods = handlerMethods.values();
        for (HandlerMethod method : methods) {
            // 遍历所有方法，判断是否有@RequiresPermissions标签
            RequiresPermissions anno = method.getMethodAnnotation(RequiresPermissions.class);
            if (anno != null) {
                // 需要的权限表达式
                String resource = anno.value()[0];

                if (resourcesList.contains(resource)) {
                    continue;
                }
                logger.info(resource);
                resourcesList.add(resource);
                UPermission p = new UPermission();
                p.setResource(resource);
                p.setName(method.getMethodAnnotation(PermissionName.class).value());
                uPermissionService.save(p);
            }
        }
        resourcesList = uPermissionService.selectAllResources();

        return resourcesList;
    }
}
