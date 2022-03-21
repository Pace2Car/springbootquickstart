package com.pace2car.springbootdemo.zk;

import org.I0Itec.zkclient.ZkClient;

import java.net.SocketException;

/**
 * @author Chen Jiahao
 * @since 2021/3/29 16:49
 */
public class ZkClientTest {

    private static final String top = "/somp";

    private static final String NODE_NAME = "四川省信息中心";
    private static final String NODE_REGION_CODE = "510000";
    private static final Integer NODE_LEVEL = 1;

    private static ZkClient zkClient;

    public static void main(String[] args) throws InterruptedException, SocketException {
        NcNodeInfo ncNodeInfo = new NcNodeInfo();
        ncNodeInfo.setNodeName(NODE_NAME);
        ncNodeInfo.setNodeLevel(NODE_LEVEL);
        ncNodeInfo.setHeartTime(System.currentTimeMillis());
        ncNodeInfo.setRegionCode(NODE_REGION_CODE);
        ncNodeInfo.setNodeIp(ZkTool.getRealIp());
        ZkTool.getNodePath(ncNodeInfo);

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
}
