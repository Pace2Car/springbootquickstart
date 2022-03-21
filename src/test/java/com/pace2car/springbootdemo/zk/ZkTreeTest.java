package com.pace2car.springbootdemo.zk;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 * @author Chen Jiahao
 * @since 2021/3/29 16:49
 */
public class ZkTreeTest {

    private static final String connectionString = "127.0.0.1:2181";

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = new ZkClient(connectionString, 10000, 10000, new SerializableSerializer());

        // 判断某一节点是否存在，返回一个布尔值
        boolean flag = zkClient.exists("/somp");
        System.out.println(flag);
        if (!flag) {
            zkClient.create("/somp", "test", CreateMode.PERSISTENT);
        }
        String path = zkClient.create("/somp/qinghai", "test", CreateMode.PERSISTENT);
        System.out.println("创建节点" + path);

        // 修改节点的数据
        // 修改数据，不校验版本号
        zkClient.writeData("/somp/qinghai", "321");
        // 修改数据，校验版本号
//        zkClient.writeData("/testNode1", "2", 1);

        // 获取子节点的列表
        List<String> childs = zkClient.getChildren("/somp");
        System.out.println(childs.toString());

        // 添加监听
        zkClient.subscribeChildChanges("/somp/qinghai", new ZkChildListener());
        zkClient.subscribeDataChanges("/somp/qinghai", new ZkDataListener());

        Thread.sleep(Integer.MAX_VALUE);
    }
}
