package cn.sambo.difference.platform.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicUpdate(true)
@DynamicInsert(true)
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@Table(name = "SYS_PLATFORM_ROLE")
public class PlatformRole extends BaseEntity implements GrantedAuthority, Serializable {
    private static final long serialVersionUID = 1L;

    /** 角色 */
    private String role;

    /** 角色名称 */
    private String roleName;

    /** 角色说明 */
    private String roleDesc;

    /** 允许资源 */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="SYS_PLATFORM_ROLE_PERMISSION",
            joinColumns=@JoinColumn(name="ROLE_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="PERMISSION_ID", referencedColumnName="ID"))
    private Set<PlatformPermission> platformPermissions = new HashSet<>();

    /** 平台用户 */
    @JsonIgnore
    @ManyToMany(mappedBy="authorities", fetch = FetchType.LAZY)
    private Set<PlatformUser> platformUsers = new HashSet<>();


    public PlatformRole() {
    }

    public PlatformRole(String role, String roleName) {
        this.role = role;
        this.roleName = roleName;
    }

    public PlatformRole(String role, String roleName, Set<PlatformPermission> platformPermissions) {
        this.role = role;
        this.roleName = roleName;
        this.platformPermissions = platformPermissions;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    public void setAuthority(String authority) {
        this.role = role;
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
     * 获取 平台用户
     *
     * @return platformUsers 平台用户
     */
    public Set<PlatformUser> getPlatformUsers() {
        return this.platformUsers;
    }

    /**
     * 设置 平台用户
     *
     * @param platformUsers 平台用户
     */
    public void setPlatformUsers(Set<PlatformUser> platformUsers) {
        this.platformUsers = platformUsers;
    }

    /**
     * 获取 允许资源
     *
     * @return platformPermissions 允许资源
     */
    public Set<PlatformPermission> getPlatformPermissions() {
        return this.platformPermissions;
    }

    /**
     * 设置 允许资源
     *
     * @param platformPermissions 允许资源
     */
    public void setPlatformPermissions(Set<PlatformPermission> platformPermissions) {
        this.platformPermissions = platformPermissions;
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
}
