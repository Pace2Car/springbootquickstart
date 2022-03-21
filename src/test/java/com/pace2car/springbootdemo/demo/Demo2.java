package com.pace2car.springbootdemo.demo;

/**
 * @author Chen Jiahao
 * @since 2021/6/11 11:42
 */
public class Demo2 {
    int j = 0;
    int i = getJ();

    public int getJ() {
        j = 10;
        System.out.println(i);
        return j;
    }

    public static void main(String[] args) {
        Demo2 demo = new Demo2();
        System.out.println(demo.j);
    }

}
