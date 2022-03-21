package com.pace2car.springbootdemo.demo;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.PictureRenderData;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chen Jiahao
 * @since 2022/3/17 14:22
 */
public class PoiTlDemo {
    public static void main(String[] args) throws IOException {

        ConfigureBuilder builder = Configure.builder();
        builder.useSpringEL(false);
        builder.setValidErrorHandler(new Configure.DiscardHandler());

        Map<String, Object> wordDataMap = new HashMap<>();// 存储报表全部数据
        Map<String, Object> basicMap = new HashMap<>();// 存储报表basic的数据
        Map<String, Object> reviewMap = new HashMap<>();// 存储报表review的数据
        Map<String, Object> eventMonitorMap = new HashMap<>();// 存储报表eventMonitor的数据
        Map<String, Object> vulnerabilityMonitorMap = new HashMap<>();// 存储报表vulnerabilityMonitor的数据

        basicMap.put("customerName", "测试报告");
        basicMap.put("docDateYmd", "1111-11-11");
        basicMap.put("local", new PictureRenderData(180, 100, "C:\\Users\\admin\\Desktop\\电子书\\大和.png"));


        reviewMap.put("newAddCount", 0);
        reviewMap.put("hisNoRepairCount", 0);
        reviewMap.put("vulnCount", 0);

        vulnerabilityMonitorMap.put("cover", 0);
        vulnerabilityMonitorMap.put("newAddCount", 0);
        vulnerabilityMonitorMap.put("hisNoRepairCount", 0);


        wordDataMap.put("customerName", "测试报告");
        wordDataMap.put("basic", basicMap);
        wordDataMap.put("review", reviewMap);
        wordDataMap.put("eventMonitor", eventMonitorMap);
        wordDataMap.put("vulnerabilityMonitor", vulnerabilityMonitorMap);

        XWPFTemplate template;

        //template = XWPFTemplate.compile("C:\\Users\\admin\\Desktop\\poi模板.docx").render(wordDataMap);
        template = XWPFTemplate.compile("C:\\Users\\admin\\Desktop\\pzh-report.docx", builder.build()).render(wordDataMap);
        //template.writeAndClose(new FileOutputStream("C:\\Users\\admin\\Desktop\\poi模板-生成.docx"));
        template.writeAndClose(new FileOutputStream("C:\\Users\\admin\\Desktop\\pzh-report-生成.docx"));
    }
}
