package com.pace2car.springbootdemo.mongo;

import com.pace2car.springbootdemo.entity.UserLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Pace2Car
 * @date 2019/4/18 16:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDBTest {

    private static final Logger logger = LoggerFactory.getLogger(MongoDBTest.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testInsert() {
        UserLog userLog1 = new UserLog();
        userLog1.setOperation("测试新增mongo id自增")
                .setParam("paramString")
                .setUserId("2222")
                .setUsername("admin");

        UserLog userLog2 = new UserLog();
        userLog2.setOperation("测试新增mongo id自增")
                .setParam("paramString")
                .setUserId("2222")
                .setUsername("admin");

        mongoTemplate.save(userLog1);
        mongoTemplate.save(userLog2);

        logger.info("mongo插入一条数据{},{}", userLog1, userLog2);
    }

    @Test
    public void testQuery() {
        String regex = "^.*ad.*$";
        Query query = new Query(Criteria
                .where("username").regex(regex)
                .and("operateTime")
                .gte(LocalDateTime.of(2019,4,18,17,0,0))
                .lte(LocalDateTime.of(2019,4,18,19,0,0)));
        List<UserLog> userLogs = mongoTemplate.find(query, UserLog.class);
        logger.info("mongo条件模糊匹配查询，结果：{}", userLogs);
    }

}
