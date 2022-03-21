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
public class PoiDemo2 {


    public static void main(String[] args) throws IOException {
        Map<String, Object> wordDataMap = new HashMap<String, Object>();// 存储报表全部数据
        Map<String, Object> parametersMap = new HashMap<String, Object>();// 存储报表中不循环的数据

        parametersMap.put("customerName", "测试报告");

        wordDataMap.put("basic", parametersMap);
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