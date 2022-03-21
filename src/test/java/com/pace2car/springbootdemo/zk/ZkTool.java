package com.pace2car.springbootdemo.zk;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Chen Jiahao
 * @since 2021/3/31 10:39
 */
public class ZkTool {

    private static final String connectionString = "10.51.34.115:2181";
    private static final String top = "/somp";
    private static ZkClient zkClient;

    static {
        zkClient = new ZkClient(connectionString, 10000, 10000, new SerializableSerializer());
    }

    public static void createNode(NcNodeInfo ncNodeInfo) {
        if (top.equals(ncNodeInfo.getParentNodePath()) && !zkClient.exists(ncNodeInfo.getParentNodePath())) {
            NcNodeInfo topNcNodeInfo = new NcNodeInfo();
            topNcNodeInfo.setNodePath(top);
            topNcNodeInfo.setParentNodePath("/");
            topNcNodeInfo.setHeartTime(0L);
            topNcNodeInfo.setNodeLevel(ncNodeInfo.getNodeLevel() - 1);
            zkClient.create(top, JSON.toJSONString(topNcNodeInfo), CreateMode.PERSISTENT);
        }
        if (!zkClient.exists(ncNodeInfo.getNodePath())) {
            if (!zkClient.exists(ncNodeInfo.getParentNodePath())) {
                NcNodeInfo parentNcNodeInfo = new NcNodeInfo();
                parentNcNodeInfo.setNodePath(ncNodeInfo.getParentNodePath());
                parentNcNodeInfo.setParentNodePath(ncNodeInfo.getNodePath().substring(0, ncNodeInfo.getParentNodePath().lastIndexOf("/")));
                parentNcNodeInfo.setHeartTime(0L);
                parentNcNodeInfo.setNodeLevel(ncNodeInfo.getNodeLevel() - 1);
                createNode(parentNcNodeInfo);
            }
            zkClient.create(ncNodeInfo.getNodePath(), JSON.toJSONString(ncNodeInfo), CreateMode.PERSISTENT);
        } else {
            zkClient.writeData(ncNodeInfo.getNodePath(), JSON.toJSONString(ncNodeInfo));
        }
    }

    public static void writeNodeData(NcNodeInfo ncNodeInfo) {
        zkClient.writeData(ncNodeInfo.getNodePath(), JSON.toJSONString(ncNodeInfo));
    }

    public static List<NcNodeInfo> getNodes(String topNodePath) {
        List<NcNodeInfo> result = new ArrayList<>();
        // 获取树
        // 1.获取子节点的列表
        List<String> childs = zkClient.getChildren(topNodePath);
        // 2.递归获取树
        System.out.println("子节点数:" + childs.size());
        System.out.println("子节点名称:" + childs.toString());
        for (String child : childs) {
            Object readData = zkClient.readData(topNodePath + "/" + child);
            NcNodeInfo ncNodeInfo = JSON.parseObject(readData.toString(), NcNodeInfo.class);
            printNode(ncNodeInfo);
        }
        return result;
    }

    public static void printNode(NcNodeInfo ncNodeInfo) {
        if (ncNodeInfo.getNodeLevel() < 3) {
            List<String> childs = zkClient.getChildren(ncNodeInfo.getNodePath());
            childs.forEach(n -> {
                Object readData = zkClient.readData(ncNodeInfo.getNodePath() + "/" + n);
                NcNodeInfo cnode = JSON.parseObject(readData.toString(), NcNodeInfo.class);
                printNode(cnode);
            });
        }
        if (System.currentTimeMillis() - ncNodeInfo.getHeartTime() > 15000) {
            System.err.println("节点超时：" + JSON.toJSONString(ncNodeInfo));
        } else {
            System.out.println(JSON.toJSONString(ncNodeInfo));
        }
    }

    public static String getRealIp() throws SocketException {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP

        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                    localip = ip.getHostAddress();
                }
            }
        }

        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }

    /**
     * 获取节点路径
     * @param nodeInfo
     */
    public static void getNodePath(NcNodeInfo nodeInfo) {
        String parentRegionCode;
        String gParentRegionCode;
        switch (nodeInfo.getNodeLevel()) {
            case 1:
                nodeInfo.setParentNodePath(top);
                nodeInfo.setNodePath(nodeInfo.getParentNodePath() + "/" + nodeInfo.getRegionCode());
                break;
            case 2:
                gParentRegionCode = nodeInfo.getRegionCode().substring(0, 2) + "0000";
                nodeInfo.setParentNodePath(top + "/" + gParentRegionCode);
                nodeInfo.setNodePath(nodeInfo.getParentNodePath() + "/" + nodeInfo.getRegionCode());
                break;
            case 3:
                parentRegionCode = nodeInfo.getRegionCode().substring(0, 4) + "00";
                gParentRegionCode = parentRegionCode.substring(0, 2) + "0000";
                nodeInfo.setParentNodePath(top + "/" + gParentRegionCode + "/" + parentRegionCode);
                nodeInfo.setNodePath(nodeInfo.getParentNodePath() + "/" + nodeInfo.getRegionCode());
                break;
        }
    }

}
