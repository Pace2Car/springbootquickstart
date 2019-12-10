package com.pace2car.springbootdemo.crawler;

import com.pace2car.springbootdemo.SpringbootdemoApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 *  爬虫测试
 * @author chenjiahao
 * @date 2019/11/5 11:11
 */
public class CrawlerTest extends SpringbootdemoApplicationTests {

    @Autowired
    private BaiduPOICrawler crawler;

    @Test
    public void testGet() throws IOException {
        crawler.getLotList();
    }
}
