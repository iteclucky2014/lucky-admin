package com.lucky.admin.platform.common;

@SuppressWarnings("unchecked")
public class ApiResultBuilder {
    private ApiResult target;

    private ApiResultBuilder() {
        this.target = new ApiResult();
    }

    public static ApiResultBuilder create() {
        return new ApiResultBuilder();
    }

    public ApiResultBuilder code(int code) {
        target.setCode(code);
        return this;
    }

    public ApiResultBuilder msg(String msg) {
        target.setMsg(msg);
        return this;
    }

    public ApiResultBuilder pagination(ApiPagination pagination) {
        target.setPagination(pagination);
        return this;
    }

    public ApiResultBuilder data(Object data) {
        target.setData(data);
        return this;
    }

    public ApiResult build() {
        return new ApiResult(target);
    }
}
