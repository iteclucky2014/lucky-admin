package com.lucky.admin.platform.vo;

public class PlatformUserSubmitParams {

    /** 用户ID */
    private String id;
    /** 登录名 */
    private String username;
    /** 密码 */
    private String password;
    /** 真实姓名 */
    private String realName;
    /** 手机 */
    private String tel;
    /** 邮箱 */
    private String mail;
    /** 部门ID. */
    private Long deptId;
    /** 角色ID列表 */
    private String[] platformRoles;


    /**
     * 获取 用户ID
     *
     * @return id 用户ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置 用户ID
     *
     * @param id 用户ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取 登录名
     *
     * @return username 登录名
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * 设置 登录名
     *
     * @param username 登录名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取 密码
     *
     * @return password 密码
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置 密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取 真实姓名
     *
     * @return realName 真实姓名
     */
    public String getRealName() {
        return this.realName;
    }

    /**
     * 设置 真实姓名
     *
     * @param realName 真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 获取 手机
     *
     * @return tel 手机
     */
    public String getTel() {
        return this.tel;
    }

    /**
     * 设置 手机
     *
     * @param tel 手机
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 获取 邮箱
     *
     * @return mail 邮箱
     */
    public String getMail() {
        return this.mail;
    }

    /**
     * 设置 邮箱
     *
     * @param mail 邮箱
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * 获取 角色ID列表
     *
     * @return platformRoles 角色ID列表
     */
    public String[] getPlatformRoles() {
        return this.platformRoles;
    }

    /**
     * 设置 角色ID列表
     *
     * @param platformRoles 角色ID列表
     */
    public void setPlatformRoles(String[] platformRoles) {
        this.platformRoles = platformRoles;
    }


    /**
     * 获取 部门ID.
     *
     * @return deptId 部门ID.
     */
    public Long getDeptId() {
        return this.deptId;
    }

    /**
     * 设置 部门ID.
     *
     * @param deptId 部门ID.
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
