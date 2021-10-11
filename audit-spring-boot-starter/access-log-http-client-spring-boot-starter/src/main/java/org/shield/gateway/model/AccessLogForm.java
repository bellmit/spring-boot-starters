package org.shield.gateway.model;

import java.util.Date;

import lombok.Data;

/**
 * @author zacksleo@gmail.com
 */
@Data
public class AccessLogForm {

    /**
     * 租户标识
     */
    private String tenant;

   /**
     * IP
     */
    private String ip;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 访问地址
     */
    private String url;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求编号
     */
    private String requestId;

    /**
     * 操作人编号
     */
    private String operatorId;

    /**
     * 操作人名称
     */
    private String operatorName;

    /**
     * 详细信息
     */
    private String remark;

    /**
     * 记录时间
     */
    private Date recordTime;
}
