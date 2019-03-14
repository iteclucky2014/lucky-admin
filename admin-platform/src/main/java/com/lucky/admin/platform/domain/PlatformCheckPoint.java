package com.lucky.admin.platform.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@DynamicUpdate(true)
@DynamicInsert(true)
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer", "fieldHandler" })
@Table(name = "SYS_PLATFORM_CHECK_POINT")
public class PlatformCheckPoint extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 隶属分区 */
	private Long belongRegion;
	/** 设备类型 */
	private char deviceType;
	/** 编号 */
	private String code;
	/** 名称 */
	private String name;
	/** 位置 */
	private String location;
	/** 地点 */
	private String address;
	/** 状态 */
	private String stats;
	/** 描述 */
	private String description;

	public Long getBelongRegion() {
		return belongRegion;
	}
	public void setBelongRegion(Long belongRegion) {
		this.belongRegion = belongRegion;
	}
	public char getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(char deviceType) {
		this.deviceType = deviceType;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStats() {
		return stats;
	}
	public void setStats(String stats) {
		this.stats = stats;
	}
	
}
