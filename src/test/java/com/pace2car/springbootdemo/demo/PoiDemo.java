package com.pace2car.springbootdemo.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试使用POI替换WORD目标字段
 * @author Chen Jiahao
 * @since 2021/6/11 11:42
 */
public class PoiDemo {


    public static void main(String[] args) throws IOException {
        Map<String, Object> wordDataMap = new HashMap<String, Object>();// 存储报表全部数据
        Map<String, Object> parametersMap = new HashMap<String, Object>();// 存储报表中不循环的数据
        List<Map<String, Object>> table1 = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1=new HashMap<>();
        map1.put("name", "张三");
        map1.put("age", "23");
        map1.put("email", "12121@qq.com");
        Map<String, Object> map2=new HashMap<>();
        map2.put("name", "李四");
        map2.put("age", "45");
        map2.put("email", "45445@qq.com");
        Map<String, Object> map3=new HashMap<>();
        map3.put("name", "Tom");
        map3.put("age", "34");
        map3.put("email", "6767@qq.com");
        table1.add(map1);
        table1.add(map2);
        table1.add(map3);
        List<Map<String, Object>> table2 = new ArrayList<Map<String, Object>>();
        Map<String, Object> map4=new HashMap<>();
        map4.put("name", "tom");
        map4.put("number", "sd1234");
        map4.put("address", "上海");
        Map<String, Object> map5=new HashMap<>();
        map5.put("name", "seven");
        map5.put("number", "sd15678");
        map5.put("address", "北京");
        Map<String, Object> map6=new HashMap<>();
        map6.put("name", "lisa");
        map6.put("number", "sd9078");
        map6.put("address", "广州");
        table2.add(map4);
        table2.add(map5);
        table2.add(map6);
        parametersMap.put("basic.customerName", "测试报告");
        parametersMap.put("userName", "JUVENILESS");
        parametersMap.put("time", "2018-03-24");
        parametersMap.put("sum", "3");
        wordDataMap.put("table1", table1);
        wordDataMap.put("table2", table2);
        wordDataMap.put("parametersMap", parametersMap);
        File file = new File("C:\\Users\\admin\\Desktop\\pzh-report.docx");//改成你本地文件所在目录
        // 读取word模板
        FileInputStream fileInputStream = new FileInputStream(file);
        WordTemplate template = new WordTemplate(fileInputStream);
        // 替换数据
        template.replaceDocument(wordDataMap);
        //生成文件
        File outputFile=new File("C:\\Users\\admin\\Desktop\\pzh-report-生成.docx");//改成你本地文件所在目录
        FileOutputStream fos  = new FileOutputStream(outputFile);
        template.getDocument().write(fos);
    }
}