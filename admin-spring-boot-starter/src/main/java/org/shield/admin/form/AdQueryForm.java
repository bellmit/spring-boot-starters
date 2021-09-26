package org.shield.admin.form;

import org.shield.admin.model.Ad;
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
public class AdQueryForm extends PageableQuery {

    /**
     * 广告位编号
     */
    @ApiModelProperty(value = "广告位编号", example = "1", required = true)
    private Integer positionId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", example = "国庆节快乐")
    private Integer name;

    /**
     * 类别: 0图片 1文本 2视频
     */
    @ApiModelProperty(value = "类别: 0图片 1文本 2视频", example = "1")
    private Integer catalog;

    /**
     * 是否有效：1有效 0无效
     */
    @ApiModelProperty(value = "是否有效：1有效 0无效", example = "1")
    private Boolean isActive;

    public Condition toCondition() {
        Condition condition = new Condition(Ad.class);
        Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("positionId", getPositionId());
        criteria.andEqualTo("name", getName());
        criteria.andEqualTo("catalog", getCatalog());
        criteria.andEqualTo("isActive", getIsActive());
        return condition;
    }
}
