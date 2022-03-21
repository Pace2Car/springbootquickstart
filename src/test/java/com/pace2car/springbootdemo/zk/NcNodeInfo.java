package com.pace2car.springbootdemo.zk;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author Chen Jiahao
 * @since 2021/3/30 17:03
 */
@Data
public class NcNodeInfo {
    /**
     * 主键
     */
    private Integer pkId;
    /**
     * 节点名称
     */
    private String nodeName;
    /**
     * 节点等级（1-3：省市区，默认区级）
     */
    private Integer nodeLevel;
    /**
     * 行政区域名称
     */
    private String regionName;
    /**
     * 行政区域编码
     */
    private String regionCode;
    /**
     * 联络人ID
     */
    private String contactUserId;
    /**
     * 节点全路径
     */
    private String nodePath;
    /**
     * 上级节点路径
     */
    private String parentNodePath;
    /**
     * 上级节点行政编码
     */
    private String parentAdministrativeCode;
    /**
     * 节点IP
     */
    private String nodeIp;
    /**
     * 心跳时间
     */
    private Long heartTime;
}
