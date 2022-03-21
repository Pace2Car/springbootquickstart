package com.pace2car.springbootdemo.demo;

/**
 * @author Chen Jiahao
 * @since 2021/6/11 11:42
 */
public class Demo1 {
    int i = getJ();
    int j = 0;

    public int getJ() {
        j = 10;
        System.out.println(i);
        return j;
    }

    public static void main(String[] args) {
        Demo1 demo = new Demo1();
        System.out.println(demo.j);
        System.out.println(demo.i);
    }
}
