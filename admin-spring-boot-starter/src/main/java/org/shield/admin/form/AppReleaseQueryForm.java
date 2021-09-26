package org.shield.admin.form;

import org.shield.admin.model.AppRelease;
import org.shield.mybatis.form.PageableQuery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
public class AppReleaseQueryForm extends PageableQuery {

    /**
     * 版本名称
     */
    @ApiModelProperty(value = "版本名称", example = "1.0.0")
    private String version;

    /**
     * 版本编号
     */
    @ApiModelProperty(value = "版本编号", example = "100")
    private Integer versionNum;

    /**
     * 类别: 1安卓 2iOS
     */
    @ApiModelProperty(value = "类别: 1安卓 2iOS", example = "1")
    private Integer catalog;

    /**
     * 是否强制升级: 0否 1是
     */
    @ApiModelProperty(value = "是否强制升级: 0否 1是", example = "0")
    private Boolean forceUpgrade;

    /**
     * 文件hash
     */
    @ApiModelProperty(value = "文件hash", example = "098f6bcd4621d373cade4e832627b4f6")
    private String hash;

    /**
     * 是否有效：1有效 0无效
     */
    @ApiModelProperty(value = "是否有效：1有效 0无效", example = "1")
    private Boolean isActive;

    public Condition toCondition() {
        Condition condition = new Condition(AppRelease.class);
        Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("version", getVersion());
        criteria.andEqualTo("versionNum", getVersionNum());
        criteria.andEqualTo("catalog", getCatalog());
        criteria.andEqualTo("forceUpgrade", getForceUpgrade());
        criteria.andEqualTo("hash", getHash());
        criteria.andEqualTo("isActive", getIsActive());
        return condition;
    }
}
