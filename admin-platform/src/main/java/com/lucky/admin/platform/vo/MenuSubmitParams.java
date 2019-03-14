package com.lucky.admin.platform.vo;

public class MenuSubmitParams {

    /** ID */
    private String id;
    /** 父ID */
    private String pid;
    /** 菜单名称 */
    private String name;
    /** 菜单标题 */
    private String title;
    /** 菜单图标 */
    private String icon;
    /** 菜单跳转 */
    private String jump;
    /** 表示顺序 */
    private Integer dispOrder;
    /** 删除标志 */
    private Integer delFlag;
    /** 角色ID列表 */
    private String[] platformRoles;


    /**
     * 获取 ID
     *
     * @return id ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置 ID
     *
     * @param id ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取 父ID
     *
     * @return pid 父ID
     */
    public String getPid() {
        return this.pid;
    }

    /**
     * 设置 父ID
     *
     * @param pid 父ID
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * 获取 菜单名称
     *
     * @return name 菜单名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置 菜单名称
     *
     * @param name 菜单名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 菜单标题
     *
     * @return title 菜单标题
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * 设置 菜单标题
     *
     * @param title 菜单标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取 菜单图标
     *
     * @return icon 菜单图标
     */
    public String getIcon() {
        return this.icon;
    }

    /**
     * 设置 菜单图标
     *
     * @param icon 菜单图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取 菜单跳转
     *
     * @return jump 菜单跳转
     */
    public String getJump() {
        return this.jump;
    }

    /**
     * 设置 菜单跳转
     *
     * @param jump 菜单跳转
     */
    public void setJump(String jump) {
        this.jump = jump;
    }

    /**
     * 获取 表示顺序
     *
     * @return dispOrder 表示顺序
     */
    public Integer getDispOrder() {
        return this.dispOrder;
    }

    /**
     * 设置 表示顺序
     *
     * @param dispOrder 表示顺序
     */
    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    /**
     * 获取 删除标志
     *
     * @return delFlag 删除标志
     */
    public Integer getDelFlag() {
        return this.delFlag;
    }

    /**
     * 设置 删除标志
     *
     * @param delFlag 删除标志
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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
}
