package com.lucky.admin.platform.vo;

import com.lucky.admin.platform.common.BaseEntity;

/**
 * Created by Lucky on 2019/3/24.
 */
public class Role extends BaseEntity {

    private String name;
    
    public Role() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
