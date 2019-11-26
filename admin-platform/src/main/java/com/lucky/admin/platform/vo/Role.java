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

    private int auth;
    
    private int menuId;
    
    private int root;
    
    private int seq;
    
    private String icon;
    
    private String title;
    
    private String address;

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

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
