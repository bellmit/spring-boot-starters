package org.shield.audit.form;

import java.util.Date;

import org.shield.mongo.domain.PageableQuery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginLogQueryForm extends PageableQuery {

    /**
     * 租户标识
     */
    @ApiModelProperty(value = "租户标识", example = "org.shield.admin")
    private String tenant;

    /**
     * 业务模块
     */
    @ApiModelProperty(value = "IP地址", example = "183.6.7.210")
    private String ip;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", example = "admin")
    private String username;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间", example = "2021-09-26 10:57:24", dataType = "date")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间", example = "2021-09-26 10:57:24", dataType = "date")
    private Date endTime;

    @Override
    public String getOrderBy() {
        if (super.getOrderBy() == null) {
            return "id desc";
        }
        return super.getOrderBy();
    }
}
