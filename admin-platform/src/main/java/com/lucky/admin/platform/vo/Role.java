package com.lucky.admin.platform.vo;

import com.lucky.admin.platform.common.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Lucky on 2019/3/24.
 */
public class Role extends BaseEntity implements GrantedAuthority {

    private String roleName;
    
    private String roleDesc;

    private Set<Menu> permissions = new HashSet<>();
    
    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(String roleName, Set<Menu> permissions) {
        this.roleName = roleName;
        this.permissions = permissions;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Set<Menu> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Menu> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String getAuthority() {
        return roleName;
    }

    public void setAuthority(String authority) {
        this.roleName = authority;
    }
}
