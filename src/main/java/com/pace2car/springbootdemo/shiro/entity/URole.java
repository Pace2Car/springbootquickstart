package com.pace2car.springbootdemo.shiro.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.pace2car.springbootdemo.dto.BasePageParam;



import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Pace2Car
 * @since 2019-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class URole {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色类型
     */
    private String type;


}
