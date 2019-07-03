package com.pace2car.springbootdemo.mongo;

import com.pace2car.springbootdemo.entity.UserLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void testInsert() throws Exception {
        UserLog userLog1 = new UserLog();
        userLog1.setOperation("测试新增mongo id自增")
                .setParam("paramString")
                .setUserId("2222")
                .setUsername("aemin");

        UserLog userLog2 = new UserLog();
        userLog2.setOperation("测试新增mongo id自增")
                .setParam("paramString")
                .setUserId("2222")
                .setUsername("aemin");

        mongoTemplate.save(userLog1);
        Thread.sleep(1000);
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

    @Test
    public void testPageQuery() {
        // 拼接查询条件
        int pageNum = 3;
        int pageSize = 5;
        Query query = new Query();
        Criteria criteria = new Criteria();
        String regex = "^.*a.*$";
        criteria.and("username").regex(regex);
        query.addCriteria(criteria);
        // 分页信息
        query.with(new Sort(Sort.Direction.DESC, "operateTime"));
        query.skip((pageNum - 1) * pageSize).limit(pageSize);
        List<UserLog> userLogs = mongoTemplate.find(query, UserLog.class);
        // 封装分页查询结果集
        Map<String, Object> result = new HashMap<>(4);
        long totals = mongoTemplate.count(query, UserLog.class);
        result.put("page", pageNum);
        result.put("pages", (long) Math.ceil((double) totals / pageSize));
        result.put("total", totals);
        result.put("data", userLogs);
        logger.info("mongo条件模糊匹配分页查询，结果：{}", result);
    }
}
