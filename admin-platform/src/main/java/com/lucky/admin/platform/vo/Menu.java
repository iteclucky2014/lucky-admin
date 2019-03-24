package com.lucky.admin.platform.vo;

import com.lucky.admin.platform.common.BaseEntity;

/**
 * Created by Administrator on 2019/3/24.
 */
public class Menu extends BaseEntity {

    private int order;
    
    private String name;
    
    private String address;
    
    private int root;
    
    public Menu() {
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }
}
