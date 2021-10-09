package org.shield.audit.model;

import java.util.Date;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zacksleo@gmail.com
 */
@Data
@ApiModel
@Document("login_log")
public class LoginLog {

    /**
     * 租户标识
     */
    @ApiModelProperty(value = "租户标识", example = "org.shield.admin", required = true)
    @Field(name = "`tenant`")
    @Indexed
    private String tenant;

    /**
     * 业务模块
     */
    @ApiModelProperty(value = "IP地址", example = "183.6.7.210")
    @Field(name = "`ip`")
    @Indexed
    private String ip;

    /**
     * 位置
     */
    @ApiModelProperty(value = "位置", example = "河北省 衡水市")
    @Field(name = "`location`")
    @Indexed
    private String location;

    /**
     * 浏览器
     */
    @ApiModelProperty(value = "浏览器", example = "Chrome 91")
    @Field(name = "`browser`")
    @Indexed
    private String browser;

    /**
     * 操作系统
     */
    @ApiModelProperty(value = "操作系统", example = "Windows 10")
    @Field(name = "`os`")
    @Indexed
    private String os;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", example = "ADMI13EBB25728C21000")
    @Field(name = "`username`")
    @Indexed
    private String username;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "备注")
    @Field(name = "`remark`")
    private String remark;

    /**
     * 记录时间
     */
    @ApiModelProperty(value = "记录时间", example = "2021-09-26 10:57:24", dataType = "date")
    @Field(name = "`record_time`")
    private Date recordTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2021-09-26 10:57:24", dataType = "date")
    @Field(name = "`create_time`")
    private Date createTime;
}
