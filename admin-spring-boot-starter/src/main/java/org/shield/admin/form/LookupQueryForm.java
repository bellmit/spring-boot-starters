package org.shield.admin.form;

import org.shield.admin.model.Lookup;
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
public class LookupQueryForm extends PageableQuery {

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", example = "SimProviderType")
    private String catalog;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", example = "中国移动")
    private String name;

    /**
     * 值
     */
    @ApiModelProperty(value = "值", example = "1")
    private Integer code;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "")
    private String remark;

    /**
     * 是否有效：1有效 0无效
     */
    @ApiModelProperty(value = "是否有效：1有效 0无效", example = "1")
    private Boolean isActive;

    public Condition toCondition() {
        Condition condition = new Condition(Lookup.class);
        Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("catalog", getCatalog());
        criteria.andEqualTo("name", getName());
        criteria.andEqualTo("code", getCode());
        criteria.andEqualTo("remark", getRemark());
        criteria.andEqualTo("isActive", getIsActive());
        return condition;
    }
}
