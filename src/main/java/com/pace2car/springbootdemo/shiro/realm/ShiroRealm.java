package com.pace2car.springbootdemo.shiro.realm;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.pace2car.springbootdemo.shiro.entity.UUser;
import com.pace2car.springbootdemo.shiro.service.UPermissionService;
import com.pace2car.springbootdemo.shiro.service.URoleService;
import com.pace2car.springbootdemo.shiro.service.UUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Pace2Car
 * @date 2019/1/10 17:01
 */
public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LogManager.getLogger("userRealm");

    @Autowired
    @Lazy
    private UUserService uUserService;

    @Autowired
    @Lazy
    private URoleService uRoleService;

    @Autowired
    @Lazy
    private UPermissionService uPermissionService;

    @Override
    public String getName() {
        return "ShiroRealm";
    }

    /**
     * 授权
     *
     * @param principal
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        UUser user = (UUser) principal.getPrimaryPrincipal();

        List<String> userRoles = uRoleService.selectAllRolesByUsername(user.getUsername());

        List<String> permissions = uPermissionService.selectAllResourcesByUsername(user.getUsername());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(userRoles);
        logger.info("用户角色{}", userRoles);
        info.addStringPermissions(permissions);
        logger.info("用户权限{}", permissions);
        return info;
    }

    /**
     * 认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("用户登录，登录信息-->{}", token);
        //获取用户的输入的账号.
        String username = (String) token.getPrincipal();
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UUser user = uUserService.selectOne(new EntityWrapper<UUser>().eq("username", username));
        logger.info("用户登录，登录信息-->{}", user);
        if (user == null) {
            ////没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常
            return null;
        } else {
            user.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
            uUserService.updateById(user);
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                getName()
        );
        return authenticationInfo;
    }

    /**
     * 清除缓存
     */
    protected void clearCache() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
}
