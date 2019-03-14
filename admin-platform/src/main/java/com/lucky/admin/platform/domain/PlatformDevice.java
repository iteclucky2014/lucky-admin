package com.lucky.admin.platform.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@DynamicUpdate(true)
@DynamicInsert(true)
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@Table(name = "SYS_PLATFORM_DEVICE")
public class PlatformDevice extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 隶属分区 */
    private Long belongRegion;
    /** 类型 */
    private String type;
    /** 编号 */
    private String code;
    /** 名称 */
    private String name;
    /** 规格型号 */
    private String standard;
    /** 生产厂家 */
    private String manuFacturer;
    /** 供应商 */
    private String supplier;
    /** 购买日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp buyDate;
    /** 安装日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp installDate;
    /** 安装位置 */
    private String installLocation;
    /** 安装地点 */
    private String installAddress;
    /** 设备状态 */
    private String stats;
    /** 启用日期 */
    private String enableDate;
    /** 报警规则 */
    private String alarmRules;
    /** 描述 */
    private String description;

    public Long getBelongRegion() {
        return belongRegion;
    }

    public void setBelongRegion(Long belongRegion) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Timestamp getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Timestamp buyDate) {
        this.buyDate = buyDate;
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

    public String getInstallAddress() {
        return installAddress;
    }

    public void setInstallAddress(String installAddress) {
        this.installAddress = installAddress;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public String getEnableDate() {
        return enableDate;
    }

    public void setEnableDate(String enableDate) {
        this.enableDate = enableDate;
    }

    public String getAlarmRules() {
        return alarmRules;
    }

    public void setAlarmRules(String alarmRules) {
        this.alarmRules = alarmRules;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
