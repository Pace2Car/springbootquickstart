package com.pace2car.springbootdemo.handler;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Pace2Car
 * @date 2019/1/11 15:16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ErrorInfo implements Serializable {

    private String message;

    private String code;

    private String data;

    private String url;

}
