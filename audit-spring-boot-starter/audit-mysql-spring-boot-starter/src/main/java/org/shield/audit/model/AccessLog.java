package org.shield.audit.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zacksleo@gmail.com
 */
@Data
@ApiModel
@Table(name = "login_log")
public class AccessLog {

    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty("ID")
    @Column(name = "`id`")
    @JsonIgnore
    private Long id;

    /**
     * 业务模块
     */
    @ApiModelProperty(value = "IP地址", example = "183.6.7.210")
    @Column(name = "`ip`")
    private String ip;

    /**
     * 位置
     */
    @ApiModelProperty(value = "位置", example = "河北省 衡水市")
    @Column(name = "`location`")
    private String location;

    /**
     * 浏览器
     */
    @ApiModelProperty(value = "浏览器", example = "Chrome 91")
    @Column(name = "`browser`")
    private String browser;

    /**
     * 操作系统
     */
    @ApiModelProperty(value = "操作系统", example = "Windows 10")
    @Column(name = "`os`")
    private String os;

    /**
     * 访问地址
     */
    @ApiModelProperty(value = "访问地址", example = "https://github.com")
    @Column(name = "`url`")
    private String url;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式", example = "POST")
    @Column(name = "`request_method`")
    private String requestMethod;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数", example = "{}")
    @Column(name = "`request_params`")
    private String requestParams;

    /**
     * 操作人编号
     */
    @ApiModelProperty(value = "操作人编号", example = "ADMI13EBB25728C21000")
    @Column(name = "`operator_id`")
    private String operatorId;

    /**
     * 操作人名称
     */
    @ApiModelProperty(value = "操作人名称", example = "ADMI13EBB25728C21000")
    @Column(name = "`operator_name`")
    private String operatorName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "备注")
    @Column(name = "`remark`")
    private String remark;

    /**
     * 记录时间
     */
    @ApiModelProperty(value = "记录时间", example = "2021-09-26 10:57:24", dataType = "date")
    @Column(name = "`record_time`")
    private Date recordTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2021-09-26 10:57:24", dataType = "date")
    @Column(name = "`create_time`")
    private Date createTime;
}
