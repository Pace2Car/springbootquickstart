package com.pace2car.springbootdemo.zk;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author Pace2Car
 * @date 2019/3/28 10:11
 */
//public class ZkTest extends SpringbootdemoApplicationTests {
public class ZkTest implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    private static final String connectionString = "127.0.0.1:2181";

    public static void main(String[] args) {
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper(connectionString, 5000, new ZkTest());
            zooKeeper.exists("/test2", true);
            String result = zooKeeper.create("/test2", "{111}".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } finally {
            if (zooKeeper != null) {
                try {
                    zooKeeper.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            connectedSemaphore.countDown();
        }
        System.out.println("回调watcher实例： 路径" + watchedEvent.getPath() + " 类型："
                + watchedEvent.getType());
    }
}
