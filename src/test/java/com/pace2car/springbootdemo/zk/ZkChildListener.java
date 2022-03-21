package com.pace2car.springbootdemo.zk;

import org.I0Itec.zkclient.IZkChildListener;

import java.util.List;

/**
 * @author Chen Jiahao
 * @since 2021/3/29 16:57
 */
public class ZkChildListener implements IZkChildListener {

    public void handleChildChange(String parentPath, List<String> children) {
        System.out.println("子节点变化，路径：" + parentPath);
        System.out.println("子节点："+children.toString());
    }
}
