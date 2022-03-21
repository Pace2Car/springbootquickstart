package com.pace2car.springbootdemo.zk;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

import java.net.SocketException;

/**
 * @author Chen Jiahao
 * @since 2021/3/29 16:49
 */
public class ZkClientTest3 {

    private static final String connectionString = "10.51.34.115:2181";
    private static final String top = "/somp";

    private static final String NODE_NAME = "简阳市安全局";
    private static final String NODE_REGION_CODE = "510123";
    private static final Integer NODE_LEVEL = 3;

    private static ZkClient zkClient;

    static {
        zkClient = new ZkClient(connectionString, 10000, 10000, new SerializableSerializer());
    }

    public static void main(String[] args) throws InterruptedException, SocketException {
        NcNodeInfo ncNodeInfo = new NcNodeInfo();
        ncNodeInfo.setNodeName(NODE_NAME);
        ncNodeInfo.setNodeLevel(NODE_LEVEL);
        ncNodeInfo.setHeartTime(System.currentTimeMillis());
        ncNodeInfo.setRegionCode(NODE_REGION_CODE);
        ncNodeInfo.setNodeIp(ZkTool.getRealIp());
        ZkTool.getNodePath(ncNodeInfo);

//        ZkClient zkClient = new ZkClient(connectionString, 10000, 10000, new SerializableSerializer());
//        createNode(NODE_PATH, PARENT_NODE_PATH, System.currentTimeMillis());

        // 创建节点
        ZkTool.createNode(ncNodeInfo);
//        // 添加监听
//        zkClient.subscribeDataChanges(top, new ZkDataListener());
//        zkClient.subscribeChildChanges(top, new ZkChildListener());

        // 上报心跳
        while (true) {
            ZkTool.getNodes(top);
            ncNodeInfo.setHeartTime(System.currentTimeMillis());
            ZkTool.writeNodeData(ncNodeInfo);
            Thread.sleep(5000);
        }
    }

    private static void createNode(String nodePath, String parentPath, Long heartTime) {
        if (top.equals(parentPath) && !zkClient.exists(parentPath)) {
            zkClient.create(top, JSON.toJSONString(heartTime), CreateMode.PERSISTENT);
        }
        if (!zkClient.exists(nodePath)) {
            if (!zkClient.exists(parentPath)) {
                createNode(parentPath, parentPath.substring(0, parentPath.lastIndexOf("/")), 0L);
            }
            zkClient.create(nodePath, JSON.toJSONString(heartTime), CreateMode.PERSISTENT);
        } else {
            zkClient.writeData(nodePath, JSON.toJSONString(heartTime));
        }
    }
}
