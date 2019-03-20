package com.lucky.admin.platform.common;

public class ApiParams<T> {

    /** 用户ID */
    private String userid;
    /** 用户名 */
    private String username;
    /** 操作类型 */
    private String operationType;
    /** 流程定义ID */
    private String processDefinitionId;
    /** 数据 */
    private T data;

    public ApiParams() {
    }

    public ApiParams(String userid, String username, T data) {
        this.userid = userid;
        this.username = username;
        this.data = data;
    }

    /**
     * 获取 用户ID
     *
     * @return userid 用户ID
     */
    public String getUserid() {
        return this.userid;
    }

    /**
     * 设置 用户ID
     *
     * @param userid 用户ID
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取 用户名
     *
     * @return username 用户名
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * 设置 用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
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

    /**
     * 获取 操作类型
     *
     * @return operationType 操作类型
     */
    public String getOperationType() {
        return this.operationType;
    }

    /**
     * 设置 操作类型
     *
     * @param operationType 操作类型
     */
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    /**
     * 获取 流程定义ID
     *
     * @return processDefinitionId 流程定义ID
     */
    public String getProcessDefinitionId() {
        return this.processDefinitionId;
    }

    /**
     * 设置 流程定义ID
     *
     * @param processDefinitionId 流程定义ID
     */
    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }
}
