package cn.sambo.difference.platform.vo;

public class PlatformRoleSubmitParams {

    /** 角色ID */
    private String id;

    /** 角色 */
    private String role;

    /** 角色名称 */
    private String roleName;

    /** 角色说明 */
    private String roleDesc;

    /** 权限ID列表 */
    private String[] platformPermissions;


    /**
     * 获取 角色ID
     *
     * @return id 角色ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置 角色ID
     *
     * @param id 角色ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取 角色
     *
     * @return role 角色
     */
    public String getRole() {
        return this.role;
    }

    /**
     * 设置 角色
     *
     * @param role 角色
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 获取 角色名称
     *
     * @return roleName 角色名称
     */
    public String getRoleName() {
        return this.roleName;
    }

    /**
     * 设置 角色名称
     *
     * @param roleName 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取 角色说明
     *
     * @return roleDesc 角色说明
     */
    public String getRoleDesc() {
        return this.roleDesc;
    }

    /**
     * 设置 角色说明
     *
     * @param roleDesc 角色说明
     */
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }


    /**
     * 获取 权限ID列表
     *
     * @return platformPermissions 权限ID列表
     */
    public String[] getPlatformPermissions() {
        return this.platformPermissions;
    }

    /**
     * 设置 权限ID列表
     *
     * @param platformPermissions 权限ID列表
     */
    public void setPlatformPermissions(String[] platformPermissions) {
        this.platformPermissions = platformPermissions;
    }
}
