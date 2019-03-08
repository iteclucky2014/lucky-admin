package cn.sambo.difference.platform.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicUpdate(true)
@DynamicInsert(true)
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@Table(name = "SYS_PLATFORM_PERMISSION")
public class PlatformPermission extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 受保护资源名称 */
    private String name;

    /** 受保护资源操作URL匹配模式 */
    private String url;

    /** 受保护操作的资源类型 1:page, 2:menu, 3:button, 9:other */
    private Integer type;

    /** 平台用户角色 */
    @JsonIgnore
    @ManyToMany(mappedBy="platformPermissions", fetch = FetchType.LAZY)
    private Set<PlatformRole> platformRoles = new HashSet<>();

    public PlatformPermission(String name, String url, Integer type) {
        this.name = name;
        this.url = url;
        this.type = type;
    }

    public PlatformPermission() {
    }

    /**
     * 获取 受保护资源名称
     *
     * @return name 受保护资源名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置 受保护资源名称
     *
     * @param name 受保护资源名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 受保护资源操作URL匹配模式
     *
     * @return url 受保护资源操作URL匹配模式
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * 设置 受保护资源操作URL匹配模式
     *
     * @param url 受保护资源操作URL匹配模式
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取 受保护操作的资源类型 1:page 2:menu 3:button
     *
     * @return type 受保护操作的资源类型 1:page 2:menu 3:button
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置 受保护操作的资源类型 1:page 2:menu 3:button
     *
     * @param type 受保护操作的资源类型 1:page 2:menu 3:button
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取 平台用户角色
     *
     * @return platformRoles 平台用户角色
     */
    public Set<PlatformRole> getPlatformRoles() {
        return this.platformRoles;
    }

    /**
     * 设置 平台用户角色
     *
     * @param platformRoles 平台用户角色
     */
    public void setPlatformRoles(Set<PlatformRole> platformRoles) {
        this.platformRoles = platformRoles;
    }
}
