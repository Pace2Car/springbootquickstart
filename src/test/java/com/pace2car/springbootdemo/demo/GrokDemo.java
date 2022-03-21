package com.pace2car.springbootdemo.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;

import java.util.Map;

/**
 * @author Chen Jiahao
 * @since 2021/5/18 11:41
 */
public class GrokDemo {
    // private static final String log = "112.169.19.192 - - [06/Mar/2013:01:36:30 +0900] \"GET / HTTP/1.1\" 200 44346 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.152 Safari/537.22\"";
//    private static final String log = "2020-03-30 10:20:30.405 INFO [SERVICENAME,vgctd46vo7978fcrt545d979yg,gyub7868ghjvg545,false] [oi-13-thread-1] classname [monitorInfo]:173 - #@monitor#@dataquery#@coxquery#@{\"sysname\":\"coxquery\",\"subjson\":{\"subName\":\"2345\"},\"monitorTarget\":\"CHDL2030\",\"responseTime\":\"20\",\"code\":\"200\",\"ip\":\"10.2.57.100\"}";
    private static final String log = "{\"sourceport\":\"53\",\"devip\":\"10.46.190.143\",\"actresult\":\"无结果\",\"ruleid\":\"5375b896-2ed4-4170-90d1-ee4f03215540\",\"originalmessage\":\"<12>Apr 28 10:12:39 (none) : {\"dt\":\"VENUS_IDS_0700R0100B20130910153632\",\"level\":30,\"id\":\"152549922\",\"type\":\"Alert Log\",\"time\":1619575959302,\"source\":{\"ip\":\"61.139.2.69\",\"port\":53,\"mac\":\"00-10-f3-6f-f7-bf\"},\"destination\":{\"ip\":\"10.46.102.251\",\"port\":53054,\"mac\":\"44-1a-fa-c8-3a-01\"},\"protocol\":\"DNS\",\"subject\":\"DNS_木马_NetReaper_连接\",\"message\":\"nic=1\",\"targetport\":\"53054\",\"level\":\"3\",\"sourceip\":\"61.139.2.69\",\"occurtime\":\"2021-04-28 10:07:31\",\"name\":\"DNS_木马_NetReaper_连接\",\"targetip\":\"10.46.102.251\",\"alarmid\":\"#\",\"message\":\"occurtime=2021-04-28 10:07:31;alertsrc:1;priority:3;title:DNS_木马_NetReaper_连接;ruleid:5375b896-2ed4-4170-90d1-ee4f03215540;devip:10.46.190.143;devname:VENUS_IDS_0700R0100B20130910153632;description:{告警类别:恶意DNS,告警等级:一般,源端口:53,源ip:61.139.2.69,目的端口:53054,目的ip:10.46.102.251,结果:无结果,原始消息:<12>Apr 28 10:12:39 (none) : {\"dt\":\"VENUS_IDS_0700R0100B20130910153632\",\"level\":30,\"id\":\"152549922\",\"type\":\"Alert Log\",\"time\":1619575959302,\"source\":{\"ip\":\"61.139.2.69\",\"port\":53,\"mac\":\"00-10-f3-6f-f7-bf\"},\"destination\":{\"ip\":\"10.46.102.251\",\"port\":53054,\"mac\":\"44-1a-fa-c8-3a-01\"},\"protocol\":\"DNS\",\"subject\":\"DNS_木马_NetReaper_连接\",\"message\":\"nic=1;dnsname=tongji.juzi1234567.com;\"} };\",\"host\":\"172.16.1.10\",\"devname\":\"VENUS_IDS_0700R0100B20130910153632\",\"@timestamp\":\"2021-04-28T10:13:13.746Z\",\"alertsrc\":\"1\",\"tags\":[\"_grokparsefailure_sysloginput\"],\"type\":\"恶意DNS\"}\n";

    public static void main(String[] args) throws Exception {
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();
        grokCompiler.registerPatternFromClasspath("/patterns.properties");
        // TODO 开放自定义解析正则，每次添加完成注册到解析器，启动时自动注册所有解析正则
//        grokCompiler.register();

        // 找到合适的正则填写方式
//        String pattern = "^%{LOGDATE:time}.+?,%{DATA:id},.+?%{LOGJSON:json}$";
        String pattern = "^\\{\"sourceport\":\"%{CDATA:number}\",\"devip\":\"%{CDATA:ip}\",\"actresult\":\"%{CDATA:result}\",\"ruleid\":\"%{CDATA:ruleid}\",\"originalmessage\":\"%{CDATA:msg}\",\"targetport\":\".+,\"occurtime\":\"%{CDATA:time}\",\"name\".+,\"@timestamp\":\"%{CDATA:timestamp}\",\"alertsrc\".+\"type\":\"%{CDATA:type}\"\\}$";
//        Grok grok = grokCompiler.compile("%{COMBINEDAPACHELOG}");
        Grok grok = grokCompiler.compile(pattern);

        Match grokMatch = grok.match(log);
        final Map<String, Object> capture = grokMatch.capture();
        capture.forEach((k, v) -> {
            System.out.println(k + ": " + v);
        });
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(capture));
        System.out.println("---------------------------------------");
        System.out.println(jsonObject.getString("result"));
        System.out.println(jsonObject.getString("time"));
        System.out.println(jsonObject.getString("timestamp"));
        System.out.println(jsonObject.getString("type"));

        System.out.println(jsonObject);
        
    }
}
