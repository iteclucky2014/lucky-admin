package cn.sambo.difference.platform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicUpdate(true)
@DynamicInsert(true)
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@Table(name = "SYS_PLATFORM_USER")
public class PlatformUser extends BaseEntity implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    /** 登录名 */
    private String username;
    /** 密码 */
    private String password;
    /** 真实姓名 */
    private String realName;
    /** 手机 */
    private String tel;
    /** 邮箱 */
    private String mail;
    
    private String url;
    /** 所属部门 */
    @ManyToOne
    @JoinColumn(name = "dept_id")
    private PlatformDept dept;

    /** 角色集合 */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="SYS_PLATFORM_USER_ROLE",
            joinColumns=@JoinColumn(name="USER_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="ROLE_ID", referencedColumnName="ID"))
    private Set<PlatformRole> authorities = new HashSet<>();

    public PlatformUser() {
    }

    public PlatformUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public PlatformUser(String username, String password, Set<PlatformRole> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<PlatformRole> authorities) {
        this.authorities.clear();
        this.authorities.addAll(authorities);
    }

    /**
     * 获取 用户名
     *
     * @return username 用户名
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * 设置 用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取 密码
     *
     * @return password 密码
     */
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * 设置 密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * 获取 真实姓名
     *
     * @return realName 真实姓名
     */
    public String getRealName() {
        return this.realName;
    }

    /**
     * 设置 真实姓名
     *
     * @param realName 真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 获取 手机
     *
     * @return tel 手机
     */
    public String getTel() {
        return this.tel;
    }

    /**
     * 设置 手机
     *
     * @param tel 手机
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 获取 邮箱
     *
     * @return mail 邮箱
     */
    public String getMail() {
        return this.mail;
    }

    /**
     * 设置 邮箱
     *
     * @param mail 邮箱
     */
    public void setMail(String mail) {
        this.mail = mail;
    }


    /**
     * 获取 所属部门
     *
     * @return dept 所属部门
     */
    public PlatformDept getDept() {
        return this.dept;
    }

    /**
     * 设置 所属部门
     *
     * @param dept 所属部门
     */
    public void setDept(PlatformDept dept) {
        this.dept = dept;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
