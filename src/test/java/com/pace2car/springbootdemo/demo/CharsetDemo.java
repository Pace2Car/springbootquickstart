package com.pace2car.springbootdemo.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Chen Jiahao
 * @since 2022/3/11 10:45
 */
public class CharsetDemo {
    private static String s = "测试";
    private static String s2 = "&#x9519&#x8bef500";


    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(s);
        String encode = URLEncoder.encode(s, "UTF-8");
        System.out.println(encode);
        String decode = URLDecoder.decode(s, "UTF-8");
        System.out.println(decode);


        System.out.println(unicode2String(s2));
    }

    public static String unicode2String(String s) {
        // 定义正则表达式来搜索中文字符的转义符号
        Pattern compile = Pattern.compile("&#x.{4}");
        // 测试用中文字符
        //String sourceString = "C集团天c津大唐国际盘山发电有限责任公司";
        String sourceString = s;
        Matcher matcher = compile.matcher(sourceString);
        // 循环搜索 并转换 替换
        while (matcher.find()) {
            String group = matcher.group();
            // 获得16进制的码
            String hexcode = "0" + group.replaceAll("(&#|;)", "");
            System.out.println(hexcode);
            // 字符串形式的16进制码转成int并转成char 并替换到源串中
            sourceString = sourceString.replaceAll(group, (char) Integer.decode(hexcode).intValue() + "");
        }
        return sourceString;
    }
}
