package com.lucky.admin.platform.common;

public enum ApiResultCode {

    Success(0, "成功"),
    Fail(99, "失败"),
    DataIllegality(91, "请求数据不合法"),
    DataConflict(92, "数据冲突"),
    DataNotExist(93, "数据不存在"),
    DataError(94, "数据错误");

    /** 业务代码 */
    private int code;
    /** 消息文本 */
    private String msg;

    ApiResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取 业务代码
     *
     * @return code 业务代码
     */
    public int code() {
        return this.code;
    }

    /**
     * 获取 消息文本
     *
     * @return msg 消息文本
     */
    public String msg() {
        return this.msg;
    }
}
