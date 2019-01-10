package com.pace2car.springbootdemo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * dto父类，传输分页参数
 * @author Pace2Car
 * @date 2019/1/9 18:26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BasePageParam {

    private Integer pageNum;

    private Integer pageSize;
}
