package com.pace2car.springbootdemo.utils.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Http工具类
 *
 * @author 程高伟
 */
@Slf4j
public class HttpUtil {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36";

    // 超时设置
    private static final RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .setSocketTimeout(10000)
            .build();

    // 编码设置
    private static final ConnectionConfig connectionConfig = ConnectionConfig.custom()
            .setMalformedInputAction(CodingErrorAction.IGNORE)
            .setUnmappableInputAction(CodingErrorAction.IGNORE)
            .setCharset(Consts.UTF_8)
            .build();

    private static HttpClientBuilder getBuilder() {
        List<Header> headers = new ArrayList<>();
        Header header = new BasicHeader("User-Agent", USER_AGENT);
        headers.add(header);
        return HttpClients.custom().setDefaultConnectionConfig(connectionConfig).setDefaultHeaders(headers).setDefaultRequestConfig(requestConfig);
    }

    /**
     * 发送HttpGet请求
     *
     * @param url 请求地址
     * @return
     */
    public static String sendGet(String url) throws IOException {
        String result;
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpClient httpclient = getBuilder().build();
             CloseableHttpResponse response = httpclient.execute(httpGet)) {
            HttpEntity httpEntity = response.getEntity();
            result = EntityUtils.toString(httpEntity);
        }
        return result;
    }

    /**
     * 发送HttpPost请求，参数为json字符串
     *
     * @param url     请求地址
     * @param jsonStr json字符串
     * @return
     */
    public static String sendPost(String url, String jsonStr) throws IOException {
        String result;

        // 设置entity
        StringEntity stringEntity = new StringEntity(jsonStr, Consts.UTF_8);
        stringEntity.setContentType("application/json");

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(stringEntity);

        try (CloseableHttpClient httpclient = getBuilder().build(); CloseableHttpResponse httpResponse = httpclient.execute(httpPost);) {
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        }
        return result;
    }

    /**
     * 发送HttpPost请求，提交表单，支持文件上传
     *
     * @param url    请求地址
     * @param params 表单参数
     * @param files  上传文件
     * @return
     */
    public static String sendPost(String url, Map<String, Object> params, List<MultipartFile> files) throws IOException {
        String result;
        // 设置entity
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName("UTF-8"));
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        if (CollectionUtils.isNotEmpty(files)) { // 文件表单参数
            for (MultipartFile file : files) {
                Path path = Paths.get(file.getOriginalFilename());
                String contentType = Files.probeContentType(path);
                if (StringUtils.isEmpty(contentType)) {
                    contentType = "application/octet-stream";
                }
                builder.addBinaryBody(file.getName(), file.getInputStream(), ContentType.create(contentType), file.getOriginalFilename());
            }
        }
        if (MapUtils.isNotEmpty(params)) { // 普通表单参数
            params.forEach((k, v) -> {
                StringBody stringBody = new StringBody(v.toString(), ContentType.create("text/plain", "UTF-8"));
                builder.addPart(k, stringBody);
            });
        }

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(builder.build());

        try (CloseableHttpClient httpclient = getBuilder().build();
             CloseableHttpResponse httpResponse = httpclient.execute(httpPost)) {
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        }
        return result;
    }

    // MultipartFile里面的name就是表单的name，文件名是originalFilename
    private static MultipartFile fileToMultipartFile(File file, String name) throws IOException {
        log.info("File转MultipartFile：文件路径：{}", file.getAbsolutePath());
        FileInputStream inputStream = new FileInputStream(file);
        Path path = Paths.get(file.getAbsolutePath());
        String contentType = Files.probeContentType(path);
        if (StringUtils.isEmpty(contentType)) {
            contentType = "application/octet-stream";
        }
        MultipartFile multipartFile = new MockMultipartFile(
                name,
                file.getName(),
                contentType,
                inputStream);
        log.info("File转MultipartFile：转换后的文件信息：[name:{}, originalFilename:{} ,contentType:{}]",
                multipartFile.getName(),
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType());
        return multipartFile;
    }

    public static void main(String[] args) throws IOException {
        String url = "http://127.0.0.1:8080/file/uploadSingle";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", 123);
        params.put("username", "张三");
        List<MultipartFile> files = new ArrayList<>();
        MultipartFile file = fileToMultipartFile(new File("d:\\file\\中文.jpg"), "file");
        files.add(file);
        System.out.println(sendPost(url, params, files));
    }

}
