package com.pace2car.springbootdemo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Pace2Car
 * @since 2019-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GetUserParam implements Serializable {

    private static final long serialVersionUID = 8567017171364229728L;

    private Integer pageNum;

    private Integer pageSize;

    private Integer id;

    private String username;

    private String password;

    private Integer age;

    private String email;

    private String address;

}
