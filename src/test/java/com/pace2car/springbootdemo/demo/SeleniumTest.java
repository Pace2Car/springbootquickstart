package com.pace2car.springbootdemo.demo;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Chen Jiahao
 * @since 2021/3/16 16:13
 */
public class SeleniumTest {
    public static void main(String[] args) {
//        System.setProperty("webdriver.chrome.driver","D:\\devSoft\\chromeDriver\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver","./chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
//        options.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String dir = "C:\\somp\\data\\20210317\\" + uuid;

        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", "C:\\somp\\data\\20210317\\" + uuid);
        chromePrefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1);
        chromePrefs.put("download.prompt_for_download", false);

        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");

        ChromeDriver driver = new ChromeDriver(options);

        try {
            String fileName = "制度规范-文件分类统计";
            driver.get("http://localhost:8003/ureport/preview?_u=mysql:制度规范-文件分类统计.ureport.xml");
            // 等待7秒，加载数据
            Thread.sleep(3000);
            driver.get("http://localhost:8003/ureport/word?_u=mysql:制度规范-文件分类统计.ureport.xml");
            // 等待3秒，供下载
            Thread.sleep(1000);
            driver.close();
            driver.quit();
            // 移动文件
            File f = new File(dir + "\\" + fileName + ".docx");
            File file = new File(dir + ".docx");
            FileUtils.copyFile(f, file);
            boolean bol = FileUtils.deleteQuietly(new File(dir));
            System.out.println(bol);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
