package cn.sambo.difference.platform.vo;

import java.sql.Timestamp;

public class PlatformDeviceSubmitParams {

    /** 隶属分区 */
    private String belongRegion;
    /** 类型 */
    private String type;
    /** 编号 */
    private String code;
    /** 规格型号 */
    private String standard;
    /** 生产厂家 */
    private String manuFacturer;
    /** 安装日期 */
    private Timestamp installDate;
    /** 安装位置 */
    private String installLocation;
    /** 报警规则 */
    private String alarmRules;

    public String getBelongRegion() {
        return belongRegion;
    }

    public void setBelongRegion(String belongRegion) {
        this.belongRegion = belongRegion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getManuFacturer() {
        return manuFacturer;
    }

    public void setManuFacturer(String manuFacturer) {
        this.manuFacturer = manuFacturer;
    }

    public Timestamp getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Timestamp installDate) {
        this.installDate = installDate;
    }

    public String getInstallLocation() {
        return installLocation;
    }

    public void setInstallLocation(String installLocation) {
        this.installLocation = installLocation;
    }

    public String getAlarmRules() {
        return alarmRules;
    }

    public void setAlarmRules(String alarmRules) {
        this.alarmRules = alarmRules;
    }

}
