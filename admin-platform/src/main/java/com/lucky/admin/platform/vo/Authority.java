package com.lucky.admin.platform.vo;

import com.lucky.admin.platform.common.BaseEntity;

/**
 * Created by Administrator on 2019/3/24.
 */
public class Authority extends BaseEntity {
    
    private int roleId;
    
    private int menuId;
    
    public Authority() {
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
}
