package com.pace2car.springbootdemo.demo;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Chen Jiahao
 * @since 2021/3/11 16:02
 */
public class HtmlUtilTest {

    /**
     * 预览报表链接
     */
    private static final String LOGIN = "https://localhost:8003/api/v1/login";
    private static final String PREVIEW = "http://localhost:8003/ureport/preview?_u=mysql:%s.ureport.xml";
    private static final String EXPORT_WORD = "http://localhost:8003/ureport/word?_u=mysql:%s.ureport.xml";
    private static final String EXPORT_PDF = "http://localhost:8003/ureport/pdf?_u=mysql:%s.ureport.xml";

    @SneakyThrows
    public static void main(String[] args) {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        try {
            String previewUri = String.format(PREVIEW, "制度规范-文件分类统计");
            webClient.getPage(previewUri);
        } catch (Exception e) {
            System.out.println("预览有错");
        }
        Thread.sleep(1000);
        System.out.println("导出开始");
        String exportUri = String.format(EXPORT_WORD, "制度规范-文件分类统计");
        HtmlPage exportPage = webClient.getPage(exportUri);
        File uploadFile = new File("E://test.docx");
        FileOutputStream fileOutputStream = new FileOutputStream(uploadFile);
        IOUtils.copy(exportPage.getWebResponse().getContentAsStream(), fileOutputStream);
    }
}
