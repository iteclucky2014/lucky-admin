package com.lucky.admin.platform.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@DynamicUpdate(true)
@DynamicInsert(true)
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@Table(name = "SYS_MENU")
public class Menu extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

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
    /** 使用中 */
    @Transient
    private Integer useFlag;
    /** 删除标志 */
    private Integer delFlag;

    public Menu(String name, String title, String icon, String jump, Integer dispOrder) {
        this.name = name;
        this.title = title;
        this.icon = icon;
        this.jump = jump;
        this.dispOrder = dispOrder;
        this.useFlag = 1;
        this.delFlag = 0;
    }

    public Menu() {
        this.name = "";
        this.title = "";
        this.icon = "";
        this.jump = "";
        this.dispOrder = 0;
        this.delFlag = 0;
    }

    /** 上级菜单 */
    @JsonIgnore
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "parent_menu_id")
    private Menu parentMenu;
    /** 下级菜单集合 */
    @OneToMany(mappedBy = "parentMenu", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dispOrder ASC")
    private List<Menu> list = new ArrayList<>();
    /** 角色列表 */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="sys_menu_role",
            joinColumns=@JoinColumn(name="MENU_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="ROLE_ID", referencedColumnName="ID"))
    private Set<PlatformRole> roleSet = new HashSet<>();
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
     * 获取 使用中
     *
     * @return useFlag 使用中
     */
    public Integer getUseFlag() {
        return this.useFlag;
    }

    /**
     * 设置 使用中
     *
     * @param useFlag 使用中
     */
    public void setUseFlag(Integer useFlag) {
        this.useFlag = useFlag;
    }

    /**
     * 获取 上级菜单
     *
     * @return parentMenu 上级菜单
     */
    public Menu getParentMenu() {
        return this.parentMenu;
    }

    /**
     * 设置 上级菜单
     *
     * @param parentMenu 上级菜单
     */
    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    /**
     * 获取 下级菜单集合
     *
     * @return list 下级菜单集合
     */
    public List<Menu> getList() {
        return this.list;
    }

    /**
     * 设置 下级菜单集合
     *
     * @param list 下级菜单集合
     */
    public void setList(List<Menu> list) {
        this.list = list;
    }

    /**
     * 获取 角色列表
     *
     * @return roleSet 角色列表
     */
    public Set<PlatformRole> getRoleSet() {
        return this.roleSet;
    }

    /**
     * 设置 角色列表
     *
     * @param roleSet 角色列表
     */
    public void setRoleSet(Set<PlatformRole> roleSet) {
        this.roleSet = roleSet;
    }

    public void addChildrenMenu(Menu menu) {
        menu.setParentMenu(this);
        this.list.add(menu);
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
}