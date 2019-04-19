package com.pace2car.springbootdemo.mongo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Pace2Car
 * @date 2019/4/19 9:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Document(collection = "sequence")
public class SeqInfo {
    private String id;
    private Long seqId;
    private String collName;
}
