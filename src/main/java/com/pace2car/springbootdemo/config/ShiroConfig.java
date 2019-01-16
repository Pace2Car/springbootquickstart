package com.pace2car.springbootdemo.config;

import com.pace2car.springbootdemo.filter.KickoutSessionControlFilter;
import com.pace2car.springbootdemo.shiro.realm.ShiroRealm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Pace2Car
 * @date 2019/1/10 17:38
 */
@Configuration
public class ShiroConfig {

    private Logger logger = LogManager.getLogger("shiroConfig");

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 ShiroRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean(name = "shiroRealm")
    @DependsOn(value = "lifecycleBeanPostProcessor")
    public ShiroRealm shiroRealm() {
        return new ShiroRealm();
    }

    /**
     * 配置Redis缓存
     * @return
     */
    @Bean(name = "shiroRedisCacheManager")
    @DependsOn(value = "lifecycleBeanPostProcessor")
    public ShiroRedisCacheManager shiroRedisCacheManager() {
        return new ShiroRedisCacheManager();
    }

    @Bean(name = "redisSessionDAO")
    @DependsOn(value = "lifecycleBeanPostProcessor")
    public RedisSessionDAO sessionDAO() {
        return new RedisSessionDAO();
    }

    @Bean(name = "sessionIdCookie")
    @DependsOn(value = "lifecycleBeanPostProcessor")
    public SimpleCookie simpleCookie() {
        SimpleCookie sessionIdCookie = new SimpleCookie("shiroCookie");
        sessionIdCookie.setHttpOnly(true);
        // 30天过期时间
        sessionIdCookie.setMaxAge(2592000);
        return sessionIdCookie;
    }

    /**
     * 配置shiro session 的一个管理器
     * @return
     */
    @Bean(name = "sessionManager")
    @DependsOn(value = "lifecycleBeanPostProcessor")
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(simpleCookie());
        sessionManager.setGlobalSessionTimeout(1800000);
        sessionManager.setCacheManager(shiroRedisCacheManager());
        return sessionManager;
    }

    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    /**
     * 并发登录控制
     */
    @Bean
    public KickoutSessionControlFilter kickoutSessionControlFilter(CacheManager cacheManager) {
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        // 用于根据会话ID，获取会话进行踢出操作
        kickoutSessionControlFilter.setSessionManager(defaultWebSessionManager());
        // 使用cacheManager获取响应的cache来缓存用户登录的会话，用于保存用户-会话之间的关系
        kickoutSessionControlFilter.setCacheManager(cacheManager);
        // 是否踢出后来登录的，默认是false，即后者登录的用户踢出前者登录的用户
        kickoutSessionControlFilter.setKickoutAfter(false);
        // 同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两人登录
        kickoutSessionControlFilter.setMaxSession(1);
        // 被踢出后重定向到的地址
        kickoutSessionControlFilter.setKickoutUrl("/login?kickout=1");

        return kickoutSessionControlFilter;
    }

    /**
     * 注入 securityManager
     */
    @Bean(name = "securityManager")
    @DependsOn(value = "lifecycleBeanPostProcessor")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 用户授权/认证信息Cache, 采用Redis缓存
        securityManager.setCacheManager(shiroRedisCacheManager());
        // 设置realm.
        securityManager.setRealm(shiroRealm());
        // 设置sessionManager
        securityManager.setSessionManager(defaultWebSessionManager());

        return securityManager;
    }

    @Bean(name = "shiroFilter")
    @DependsOn(value = "lifecycleBeanPostProcessor")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, CacheManager cacheManager) {
        logger.info("ShiroConfiguration.shiroFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");

        //自定义拦截器限制并发在线人数
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //限制同一帐号同时在线的个数
        filtersMap.put("kickout", kickoutSessionControlFilter(cacheManager));
        shiroFilterFactoryBean.setFilters(filtersMap);

        //配置不登录可以访问的资源，anon 表示资源都可以匿名访问
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        // 对swagger文档开放
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc,kickout");

        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    /**
     * 开启shiro aop注解支持
     * 使用代理方式实现权限认证
     *
     * @param securityManager
     * @return
     */
    @Bean
    @DependsOn(value = "lifecycleBeanPostProcessor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }
}