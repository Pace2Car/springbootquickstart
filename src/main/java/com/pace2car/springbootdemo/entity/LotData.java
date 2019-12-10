package com.pace2car.springbootdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Pace2Car
 * @since 2019-12-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LotData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 百度uid
     */
    private String baiduUid;

    /**
     * 车场名称
     */
    private String lotName;

    /**
     * 地址
     */
    private String address;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 营业时间
     */
    private String businessHours;

    /**
     * POI类型 1.车场 2.入口 3.出口 4.出入口
     */
    private Integer poiType;

    /**
     * 省编码
     */
    private String provNo;

    /**
     * 省名称
     */
    private String provName;

    /**
     * 市编码
     */
    private String cityNo;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 区域号
     */
    private String areaNo;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 总车位
     */
    private Integer totalSpace;

    /**
     * 收费描述
     */
    private String feeDesc;

    /**
     * 车场类型 如：地上停车场
     */
    private String lotType;

    /**
     * 车位状态
     */
    private Integer parkStatus;

    /**
     * 车位状态描述
     */
    private String parkStatusStr;

    /**
     * 实时车位剩余量
     */
    private Integer spaceLeft;

    /**
     * 车位同步类型 1：仅基础数据 2：总车位 3：停车费 4：剩余车位状态&剩余车位数
     */
    private Integer lotSyncType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态：1.新建，2.修改，3.删除
     */
    private Integer status;


}
