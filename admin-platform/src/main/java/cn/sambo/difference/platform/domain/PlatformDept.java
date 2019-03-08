package cn.sambo.difference.platform.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicUpdate(true)
@DynamicInsert(true)
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@Table(name = "SYS_PLATFORM_DEPT")
public class PlatformDept extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 分区名称 */
    private String deptName;

    /** 分区编号 */
    private String deptId;

    /** 面积（km²） */
    private double acreage;

    /** 需水量（m³/h) */
    private double waterDemand;

    /** 中心点 */
    private String centerPoint;

    /** 范围 */
    private String deptRange;

    /** 缩放比例 */
    private int zoom;


    /** 上级分区 */
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="parent_dept_id")
    @JsonIgnore
    private PlatformDept parentDept;

    /** 下级分区列表 */
    @OneToMany(mappedBy = "parentDept", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval=true)
    @OrderBy("dispOrder")
    @JsonIgnore
    private List<PlatformDept> childrenDeptList = new ArrayList<>();

    /** 分区用户列表 */
    @JsonIgnore
    @OneToMany(mappedBy = "dept", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PlatformUser> deptUserList = new ArrayList<>();

    /** 删除标记 */
    private int delFlag;

    /** 显示顺序 */
    private int dispOrder;

    public PlatformDept(String deptName, int dispOrder) {
        this.deptName = deptName;
        this.dispOrder = dispOrder;
        this.delFlag = 0;
    }

    public PlatformDept() {
        this.dispOrder = 0;
        this.delFlag = 0;
    }

    /**
     * 获取 分区名称
     *
     * @return deptName 分区名称
     */
    public String getDeptName() {
        return this.deptName;
    }

    /**
     * 设置 分区名称
     *
     * @param deptName 分区名称
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

	/**
	 * @return the acreage
	 */
	public double getAcreage() {
		return acreage;
	}

	/**
	 * @param acreage the acreage to set
	 */
	public void setAcreage(double acreage) {
		this.acreage = acreage;
	}

	/**
	 * @return the waterDemand
	 */
	public double getWaterDemand() {
		return waterDemand;
	}

	/**
	 * @param waterDemand the waterDemand to set
	 */
	public void setWaterDemand(double waterDemand) {
		this.waterDemand = waterDemand;
	}

    /**
     * 获取 上级分区
     *
     * @return parentDept 上级分区
     */
    public PlatformDept getParentDept() {
        return this.parentDept;
    }

    /**
     * 设置 上级分区
     *
     * @param parentDept 上级分区
     */
    public void setParentDept(PlatformDept parentDept) {
        this.parentDept = parentDept;
    }

    /**
     * 获取 下级分区列表
     *
     * @return childrenDeptList 下级分区列表
     */
    public List<PlatformDept> getChildrenDeptList() {
        return this.childrenDeptList;
    }

    /**
     * 设置 下级分区列表
     *
     * @param childrenDeptList 下级分区列表
     */
    public void setChildrenDeptList(List<PlatformDept> childrenDeptList) {
        this.childrenDeptList = childrenDeptList;
    }

    /**
     * 获取 删除标记
     *
     * @return delFlag 删除标记
     */
    public int getDelFlag() {
        return this.delFlag;
    }

    /**
     * 设置 删除标记
     *
     * @param delFlag 删除标记
     */
    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取 显示顺序
     *
     * @return dispOrder 显示顺序
     */
    public int getDispOrder() {
        return this.dispOrder;
    }

    /**
     * 设置 显示顺序
     *
     * @param dispOrder 显示顺序
     */
    public void setDispOrder(int dispOrder) {
        this.dispOrder = dispOrder;
    }

    /**
     * 获取 分区用户列表
     *
     * @return deptUserList 分区用户列表
     */
    public List<PlatformUser> getDeptUserList() {
        return this.deptUserList;
    }

    /**
     * 设置 分区用户列表
     *
     * @param deptUserList 分区用户列表
     */
    public void setDeptUserList(List<PlatformUser> deptUserList) {
        this.deptUserList = deptUserList;
    }

	/**
	 * @return the deptId
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return the centerPoint
	 */
	public String getCenterPoint() {
		return centerPoint;
	}

	/**
	 * @param centerPoint the centerPoint to set
	 */
	public void setCenterPoint(String centerPoint) {
		this.centerPoint = centerPoint;
	}
	/**
	 * @return the zoom
	 */
	public int getZoom() {
		return zoom;
	}

	/**
	 * @param zoom the zoom to set
	 */
	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	/**
	 * @return the deptRange
	 */
	public String getDeptRange() {
		return deptRange;
	}

	/**
	 * @param deptRange the deptRange to set
	 */
	public void setDeptRange(String deptRange) {
		this.deptRange = deptRange;
	}
}
