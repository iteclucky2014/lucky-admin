package cn.sambo.difference.platform.common;

public class ApiUploadResult<T> {
    /** 代码 */
    private String code;
    /** 消息 */
    private String msg;
    /** 数据 */
    private T data;

    public ApiUploadResult() {
    }

    public ApiUploadResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 获取 代码
     *
     * @return code 代码
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置 代码
     *
     * @param code 代码
     */
    public void setCode(String code) {
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
     * 获取 数据
     *
     * @return data 数据
     */
    public T getData() {
        return this.data;
    }

    /**
     * 设置 数据
     *
     * @param data 数据
     */
    public void setData(T data) {
        this.data = data;
    }
}
