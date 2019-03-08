package cn.sambo.difference.platform.vo;

public class ChangePasswordParams {
    /** 用户名 */
    private String username;
    /** 旧密码 */
    private String oldPassword;
    /** 新密码 */
    private String password;
    /** 确认新密码 */
    private String repassword;

    /**
     * 获取 旧密码
     *
     * @return oldPassword 旧密码
     */
    public String getOldPassword() {
        return this.oldPassword;
    }

    /**
     * 设置 旧密码
     *
     * @param oldPassword 旧密码
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * 获取 新密码
     *
     * @return password 新密码
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置 新密码
     *
     * @param password 新密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取 确认新密码
     *
     * @return repassword 确认新密码
     */
    public String getRepassword() {
        return this.repassword;
    }

    /**
     * 设置 确认新密码
     *
     * @param repassword 确认新密码
     */
    public void setRepassword(String repassword) {
        this.repassword = repassword;
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
}
