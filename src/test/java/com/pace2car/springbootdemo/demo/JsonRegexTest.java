package com.pace2car.springbootdemo.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Chen Jiahao
 * @since 2020/9/23 17:28
 */
public class JsonRegexTest {

        private static final String CONTENT = "{\"host\":\"172.16.1.10\",\"alarmid\":\"#\",\"message\":\"occurtime=2020-06-29 14:42:24;alertsrc:1;priority:3;title:sosp告警规则;ruleid:cb23daa0-8987-4486-92b9-1f83237db470;devip:10.46.190.143;devname:VENUS_IDS_0700R0100B20130910153632;description:{告警类别:自定义,告警等级:自定义,源端口:0,目的端口:0,操作行为:/搜索,行为结果:无结果,报送设备名称:VENUS_IDS_0700R0100B20130910153632,原始消息:<12>Jun 29 14:46:31 (none) : {\\\"dt\\\":\\\"VENUS_IDS_0700R0100B20130910153632\\\",\\\"level\\\":30,\\\"id\\\":\\\"152321043\\\",\\\"type\\\":\\\"Alert Log\\\",\\\"time\\\":1593413191509,\\\"source\\\":{\\\"ip\\\":\\\"10.46.227.178\\\",\\\"port\\\":0,\\\"mac\\\":\\\"44-1a-fa-c8-3a-01\\\"},\\\"destination\\\":{\\\"ip\\\":\\\"0.0.0.0\\\",\\\"port\\\":0,\\\"mac\\\":\\\"00-00-00-00-00-00\\\"},\\\"count\\\":2,\\\"protocol\\\":\\\"ICMP\\\",\\\"subject\\\":\\\"SCAN_ICMP扫描探测\\\",\\\"message\\\":\\\"ICMP scan rate, (scaned host num, sec):(18, 1)\\\"}};\",\"@timestamp\":\"2020-06-29T14:48:05.322Z\"}";


        public static void main(String[] args) {
        String cont = CONTENT.replace(";", ",");
        System.out.println(cont);

        String regex = "\"([a-zA-z0-9@]{0,})\":\"(.*?)\"";
        String regex2 = "\"([a-zA-z0-9]{0,})\":\"{1}([a-zA-z0-9\\-\\s\\:\\u4e00-\\u9fa5\"]{0,})\"{1}[\\,\\}]{1}|" +
                "\"([a-zA-z0-9]{0,})\":([a-zA-z0-9\\-\\s\\:\\u4e00-\\u9fa5\"]{0,})[\\,\\}]{1}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher1 = pattern.matcher(CONTENT);
        Map<String, String> map = new HashMap<>();
        while (matcher1.find()) {
            if (matcher1.group(1) != null) {
                System.out.print("key1:" + matcher1.group(1));
                System.out.print(", ");
                System.out.println("value1:" + matcher1.group(2));
                map.put(matcher1.group(1), matcher1.group(2));
            } else {
                System.out.print("key2:" + matcher1.group(3));
                System.out.print(", ");
                System.out.println("value2:" + matcher1.group(4));
                map.put(matcher1.group(3), matcher1.group(4));
                System.out.print("key3:" + matcher1.group(5));
                System.out.print(", ");
                System.out.println("value3:" + matcher1.group(6));
                map.put(matcher1.group(5), matcher1.group(6));
            }
        }


        System.out.println("-------------------------------------------------");
//        String message = map.get("message");
//        String[] strings = message.split("\\;");
//        for (String string : strings) {
//            System.out.println(string);
//        }

    }
}
