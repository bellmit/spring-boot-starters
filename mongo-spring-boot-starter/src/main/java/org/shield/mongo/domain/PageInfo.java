package org.shield.mongo.domain;

import java.util.List;
import org.springframework.data.domain.Page;
import lombok.Data;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
@Data
public class PageInfo<T> {

    public static final int DEFAULT_NAVIGATE_PAGES = 8;

    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 每页的数量
     */
    private int pageSize;

    /**
     * 当前页的数量
     */
    private int size;

    /**
     * 当前页面第一个元素在数据库中的行号
     */
    private long startRow = 0L;

    /**
     * 当前页面最后一个元素在数据库中的行号
     */
    private long endRow = 0L;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 前一页
     */
    private int prePage;

    /**
     * 下一页
     */
    private int nextPage;

    /**
     * 是否为第一页
     */
    private boolean isFirstPage = false;

    /**
     * 是否为最后一页
     */
    private boolean isLastPage = false;

    /**
     * 是否有前一页
     */
    private boolean hasPreviousPage = false;

    /**
     * 是否有下一页
     */
    private boolean hasNextPage = false;

    /**
     * 导航页码数
     */
    private int navigatePages;

    /**
     * 所有导航页号
     */
    private int[] navigatepageNums;

    /**
     * 导航条上的第一页
     */
    private int navigateFirstPage;

    /**
     * 导航条上的最后一页
     */
    private int navigateLastPage;

    /**
     * 总记录数
     */
    protected long total;

    /**
     * 结果集
     */
    protected List<T> list;

    public PageInfo(Page<T> page) {
        pageNum = page.getPageable().getPageNumber();
        pageSize = page.getPageable().getPageSize();
        size = page.getSize();
        pages = page.getTotalPages();
        list = page.getContent();
        isFirstPage = pageNum == 1;
        isLastPage = pageNum == pages;
        hasPreviousPage = !isFirstPage;
        hasNextPage = !isLastPage;
        nextPage = hasNextPage ? pageNum + 1 : pages;
        total = page.getTotalElements();
    }

    public static <T> PageInfo<T> of(Page<T> page) {
        return new PageInfo<>(page);
    }
}
