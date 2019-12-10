package com.pace2car.springbootdemo.shiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pace2car.springbootdemo.shiro.entity.UPermission;
import com.pace2car.springbootdemo.shiro.mapper.UPermissionMapper;
import com.pace2car.springbootdemo.shiro.service.UPermissionService;
import com.pace2car.springbootdemo.shiro.service.URoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Pace2Car
 * @since 2019-01-10
 */
@Service
public class UPermissionServiceImpl extends ServiceImpl<UPermissionMapper, UPermission> implements UPermissionService {

    @Resource
    private UPermissionMapper uPermissionMapper;

    @Resource
    private URoleService uRoleService;

    @Override
    public List<String> selectAllResourcesByUsername(String username) {
        List<String> resourceList = new ArrayList<>();
        List<String> roleList = uRoleService.selectAllRolesByUsername(username);
        for (String roleShortName : roleList) {
            List<String> resources = uPermissionMapper.selectAllResourcesByRole(roleShortName);
            for (String resource : resources) {
                if (resourceList.contains(resource)) {
                    continue;
                }
                resourceList.add(resource);
            }
        }
        return resourceList;
    }

    @Override
    public List<String> selectAllResources() {
        return uPermissionMapper.selectAllResources();
    }
}
