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
public class LoginLog {

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
     * 租户标识
     */
    @ApiModelProperty(value = "租户标识", example = "org.shield.admin", required = true)
    @Column(name = "`tenant`")
    private String tenant;

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
     * 用户名
     */
    @ApiModelProperty(value = "用户名", example = "ADMI13EBB25728C21000")
    @Column(name = "`username`")
    private String username;

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
