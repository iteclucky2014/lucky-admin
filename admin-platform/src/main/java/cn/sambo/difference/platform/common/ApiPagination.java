package cn.sambo.difference.platform.common;

public class ApiPagination {

    /** 当前页码 */
    private Long page = 1L;
    /** 一页表示件数 */
    private Long limit = 10L;
    /** 总件数 */
    private Long count = 0L;

    public ApiPagination() {
    }

    public ApiPagination(Long page, Long limit, Long count) {
        if (page != null && page > 1L) {
            this.page = page;
        }
        if (limit != null && limit > 0L) {
            this.limit = limit;
        }
        if (count != null && count > 0L) {
            this.count = count;
        }
    }

    public Long getOffset() {
        return (this.page - 1) * this.limit;
    }

    /**
     * 获取 当前页码
     *
     * @return page 当前页码
     */
    public Long getPage() {
        return this.page;
    }

    /**
     * 设置 当前页码
     *
     * @param page 当前页码
     */
    public void setPage(Long page) {
        this.page = page;
    }

    /**
     * 获取 一页表示件数
     *
     * @return limit 一页表示件数
     */
    public Long getLimit() {
        return this.limit;
    }

    /**
     * 设置 一页表示件数
     *
     * @param limit 一页表示件数
     */
    public void setLimit(Long limit) {
        this.limit = limit;
    }

    /**
     * 获取 总件数
     *
     * @return count 总件数
     */
    public Long getCount() {
        return this.count;
    }

    /**
     * 设置 总件数
     *
     * @param count 总件数
     */
    public void setCount(Long count) {
        this.count = count;
    }
}
