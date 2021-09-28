package org.shield.mongo.domain;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public class PageableQuery implements Pageable {

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页数目
     */
    private Integer pageSize = 20;

    /**
     * 排序
     */
    private String orderBy;

    @ApiModelProperty(hidden = true)
    @Override
    public int getPageNumber() {
        return pageNum;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    @ApiModelProperty(hidden = true)
    @Override
    public Sort getSort() {
        List<Sort.Order> sorts = new ArrayList<>();
        String currentOrderBy = getOrderBy();
        if (currentOrderBy == null || currentOrderBy.isEmpty()) {
            return Sort.by(sorts);
        }
        String[] orderBys = currentOrderBy.split(",");
        for (String current : orderBys) {
            String[] sort = current.split("\\s");
            if ("asc".equals(sort[1])) {
                sorts.add(Sort.Order.asc(sort[0]));
            } else {
                sorts.add(Sort.Order.desc(sort[0]));
            }
        }
        return Sort.by(sorts);
    }

    @ApiModelProperty(hidden = true)
    @Override
    public long getOffset() {
        return (long) getPageSize() * (long) (getPageNum() - 1);
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}
