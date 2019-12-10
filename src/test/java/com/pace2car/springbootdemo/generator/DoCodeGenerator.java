package com.pace2car.springbootdemo.generator;

import com.pace2car.springbootdemo.generetor.CodeGenerator;

/**
 * @author Pace2Car
 * @date 2019/1/9 11:02
 */
public class DoCodeGenerator {
    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        generator.generateByTables("crawler", "lot_data");
    }
}
