package com.pace2car.springbootdemo.config;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Pace2Car
 * @date 2019/1/9 9:56
 */
public class CodeGenerator {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/springbootdemo?useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "123456";
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String AUTHOR = "Pace2Car";
    public static final String PROJECT_PATH = System.getProperty("user.dir");
    /**
     * 生成的文件输出到哪个目录
     */
    public static final String OUTPUT_FILE = PROJECT_PATH + "/src/main/java";
    /**
     * 包名，会按照com.pace2car.springbootdemo这种形式生成类
     */
    public static final String PACKAGE = "com.pace2car.springbootdemo";

    public void generateByTables(String moduleName, String... tableNames) {
        // 全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(false)
                .setAuthor(AUTHOR)
                .setOutputDir(OUTPUT_FILE)
                .setFileOverride(false)
                .setOpen(false)
                .setServiceName("%sService");
        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(DB_URL)
                .setUsername(USER_NAME)
                .setPassword(PASSWORD)
                .setDriverName(DRIVER);
        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setEntityLombokModel(true)
                .setCapitalMode(true)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return PROJECT_PATH + "/src/main/resources/mapper/" + (moduleName == null ? "" : moduleName)
                        + "/" + tableInfo.getEntityName() + "Mapper" + ".xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig
                .setXml(null);

        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setCfg(cfg)
                .setTemplate(templateConfig)
                .setTemplateEngine(new FreemarkerTemplateEngine())
                .setPackageInfo(
                        // 包配置
                        new PackageConfig()
                                .setModuleName(moduleName)
                                .setParent(PACKAGE)
                                .setController("controller")
                                .setEntity("entity")
                ).execute();

    }
}