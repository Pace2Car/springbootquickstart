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
public class UPermission {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * resource
     */
    private String resource;

    /**
     * name
     */
    private String name;


}
