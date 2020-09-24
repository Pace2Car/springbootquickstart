package com.pace2car.springbootdemo.demo;

import cn.hutool.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Chen Jiahao
 * @since 2020/9/24 14:02
 */
public class JSONObjectTest {

    private static final String CONTENT = "{\"host\":\"172.16.1.10\",\"alarmid\":\"#\",\"message\":\"occurtime=2020-06-29 14:42:24;alertsrc:1;priority:3;title:sosp告警规则;ruleid:cb23daa0-8987-4486-92b9-1f83237db470;devip:10.46.190.143;devname:VENUS_IDS_0700R0100B20130910153632;description:{告警类别:自定义,告警等级:自定义,源端口:0,目的端口:0,操作行为:/搜索,行为结果:无结果,报送设备名称:VENUS_IDS_0700R0100B20130910153632,原始消息:<12>Jun 29 14:46:31 (none) : {\\\"dt\\\":\\\"VENUS_IDS_0700R0100B20130910153632\\\",\\\"level\\\":30,\\\"id\\\":\\\"152321043\\\",\\\"type\\\":\\\"Alert Log\\\",\\\"time\\\":1593413191509,\\\"source\\\":{\\\"ip\\\":\\\"10.46.227.178\\\",\\\"port\\\":0,\\\"mac\\\":\\\"44-1a-fa-c8-3a-01\\\"},\\\"destination\\\":{\\\"ip\\\":\\\"0.0.0.0\\\",\\\"port\\\":0,\\\"mac\\\":\\\"00-00-00-00-00-00\\\"},\\\"count\\\":2,\\\"protocol\\\":\\\"ICMP\\\",\\\"subject\\\":\\\"SCAN_ICMP扫描探测\\\",\\\"message\\\":\\\"ICMP scan rate, (scaned host num, sec):(18, 1)\\\"}};\",\"@timestamp\":\"2020-06-29T14:48:05.322Z\"}";

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();

        // 初步处理消息
        JSONObject dataJson = new JSONObject(CONTENT);
        Set<Map.Entry<String, Object>> entrySet = dataJson.entrySet();
        for (Map.Entry<String, Object> stringObjectEntry : entrySet) {
            map.put(stringObjectEntry.getKey(), (String) stringObjectEntry.getValue());
            System.out.println(stringObjectEntry.getKey() + ":" + stringObjectEntry.getValue());
        }

        // 取出message
        String message = dataJson.get("message").toString();
        message = message.replace("=", ":");
        message = message.replace(";", ",");
        System.out.println(message);
        // 处理消息体1
        String regex = "([a-zA-z0-9]*):(.*?),";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher1 = pattern.matcher(message);
        while (matcher1.find()) {
            if (matcher1.group(1) != null) {
                System.out.print("key1:" + matcher1.group(1));
                System.out.print(", ");
                System.out.println("value1:" + matcher1.group(2));
                map.put(matcher1.group(1), matcher1.group(2));
            }
        }
        // 处理消息体2
        String regex2 = "description:\\{(.*?)}},";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(message);
        String description = "";
        while (matcher2.find()) {
            if (matcher2.group(0) != null) {
                System.out.println("value2-1:" + matcher2.group(1));
                description = matcher2.group(1);
            }
        }

        String regex3 = "([\\u2E80-\\u9FFF]*):(.*?),";
        Pattern pattern3 = Pattern.compile(regex3);
        Matcher matcher3 = pattern3.matcher(description);
        while (matcher3.find()) {
            if (matcher3.group(0) != null) {
                System.out.print("key3-1:" + matcher3.group(1));
                System.out.print(", ");
                System.out.println("value3-1:" + matcher3.group(2));
                map.put(matcher3.group(1), matcher3.group(2));
            }
        }

        // 处理消息体3
        System.out.println("==========================================");
        System.out.println(description);
        String originalMessage = description.substring(description.indexOf("原始消息:")+5) + "}";
        System.out.println(originalMessage);
        map.put("originalMessage", originalMessage);


        // 处理原始消息
        originalMessage = originalMessage.substring(originalMessage.indexOf("{"));
        JSONObject originalMessageJson = new JSONObject(originalMessage);
        for (Map.Entry<String, Object> stringObjectEntry : originalMessageJson.entrySet()) {
            map.put(stringObjectEntry.getKey(), String.valueOf(stringObjectEntry.getValue()));
            System.out.println(stringObjectEntry.getKey() + ":" + stringObjectEntry.getValue());
        }
        JSONObject destination = originalMessageJson.getJSONObject("destination");
        map.put("targetIp", destination.getStr("ip"));
        map.put("targetPort", destination.getStr("port"));
        map.put("targetMac", destination.getStr("mac"));
        JSONObject source = originalMessageJson.getJSONObject("source");
        map.put("sourceIp", source.getStr("ip"));
        map.put("sourcePort", source.getStr("port"));
        map.put("sourceMac", source.getStr("mac"));


        // 打出数据map
        System.out.println("================最终数据=================");
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            System.out.println(stringStringEntry.getKey() + ":" + stringStringEntry.getValue());
        }
    }
}
