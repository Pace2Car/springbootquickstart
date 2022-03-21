package com.pace2car.springbootdemo.zk;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.IZkDataListener;

/**
 * @author Chen Jiahao
 * @since 2021/3/29 17:28
 */
public class ZkDataListener implements IZkDataListener {
    /**
     * 节点的数据发生改变
     */
    public void handleDataChange(String dataPath, Object data) throws Exception {
        System.out.println("数据修改" + dataPath + ":" + data);
    }

    /**
     * 节点被删除
     */
    public void handleDataDeleted(String dataPath) throws Exception {
        System.out.println(dataPath + "被删除");
    }
}
