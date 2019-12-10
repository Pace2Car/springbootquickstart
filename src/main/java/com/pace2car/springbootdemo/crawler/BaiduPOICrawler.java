package com.pace2car.springbootdemo.crawler;

import com.alibaba.fastjson.JSONObject;
import com.pace2car.springbootdemo.entity.LotData;
import com.pace2car.springbootdemo.utils.http.HttpUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * 百度POI爬虫
 * @author chenjiahao
 * @date 2019/11/5 10:57
 */
@Component
public class BaiduPOICrawler {

    private static final String AK = "wb0yPcSzO3sOjz2PGLk7R0VutrccOZD4";

//    "http://api.map.baidu.com/place/v2/search?query=停车场&tag=停车场&region=成都&page_size=200&page_num=0&scope=2&output=json&ak="

    public List<LotData> getLotList() throws IOException {
        String result = HttpUtil.sendGet("http://api.map.baidu.com/place/v2/search?query=停车场&tag=停车场&region=成都&page_size=200&page_num=0&scope=2&output=json&ak=" + AK);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String results = jsonObject.get("results").toString();
        System.out.println(results);
        return null;
    }
}
