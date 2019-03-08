package cn.sambo.difference.platform.common;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {

    /** 代码 */
    private int code = 0;

    /** 消息 */
    private String msg;

    /** 数据 */
    private T data;

    /** 分页信息 */
    private ApiPagination pagination;

    public ApiResult(ApiResult<T> origin) {
        this.code = origin.code;
        this.msg = origin.msg;
        this.data = origin.data;
        if (origin.pagination != null) {
            this.pagination = origin.pagination;
        }
    }

    public ApiResult() {
    }

    public ApiResult(T data, ApiPagination pagination) {
        this.data = data;
        this.pagination = pagination;
    }

    public ApiResult(T data, Long page, Long limit, Long count) {
        this.data = data;
        this.pagination = new ApiPagination(page, limit, count);
    }

    /** 总件数 */
    public Long getCount() {
        return this.pagination == null ? 0 : this.pagination.getCount();
    }

    /** 当前页码 */
    public Long getPage() {
        return this.pagination == null ? 0 : this.pagination.getPage();
    }

    /** 一页表示件数 */
    public Long getLimit() {
        return this.pagination == null ? 0 : this.pagination.getLimit();
    }

    /**
     * 获取 数据列表
     *
     * @return data 数据列表
     */
    public T getData() {
        return this.data;
    }

    /**
     * 设置 数据列表
     *
     * @param data 数据列表
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 获取 代码
     *
     * @return code 代码
     */
    public int getCode() {
        return this.code;
    }

    /**
     * 设置 代码
     *
     * @param code 代码
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 获取 消息
     *
     * @return msg 消息
     */
    public String getMsg() {
        return this.msg;
    }

    /**
     * 设置 消息
     *
     * @param msg 消息
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取 分页信息
     *
     * @return pagination 分页信息
     */
    public ApiPagination getPagination() {
        return this.pagination;
    }

    /**
     * 设置 分页信息
     *
     * @param pagination 分页信息
     */
    public void setPagination(ApiPagination pagination) {
        this.pagination = pagination;
    }
}
