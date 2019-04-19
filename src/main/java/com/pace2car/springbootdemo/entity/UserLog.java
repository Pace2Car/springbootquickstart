package com.pace2car.springbootdemo.entity;

import com.pace2car.springbootdemo.mongo.annotation.AutoIncKey;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Pace2Car
 * @date 2019/4/18 15:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Document(collection = "userLogIncreaseId")
public class UserLog implements Serializable {

    @AutoIncKey
    @Id
    private Long id = 0L;
    @Field
    private String userId;
    @Field
    private String username;
    @Field
    private LocalDateTime operateTime;
    @Field
    private String operation;
    @Field
    private String param;

    public UserLog() {
        this.operateTime = LocalDateTime.now();
    }

}
