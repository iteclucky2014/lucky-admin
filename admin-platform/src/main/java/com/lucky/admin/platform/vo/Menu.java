package com.lucky.admin.platform.vo;

import com.lucky.admin.platform.common.BaseEntity;

import java.util.List;

/**
 * Created by Administrator on 2019/3/24.
 */
public class Menu extends BaseEntity {
    
    private int userId;

    private int root;

    private int seq;
    
    private String name;

    private String icon;

    private String title;
    
    private String address;
    
    private List<Menu> list;
    
    public Menu() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Menu> getList() {
        return list;
    }

    public void setList(List<Menu> list) {
        this.list = list;
    }
}
