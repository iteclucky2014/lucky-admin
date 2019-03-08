package cn.sambo.difference.platform.vo;

public class PlatformDeptSubmitParams {

    /** 部门ID */
    private Long id;

    /** 部门名称 */
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

    /** 上级部门ID */
    private Long parentDeptId;

    /** 表示顺序 */
    private Integer dispOrder;

    /** 删除标志 */
    private Integer delFlag;

    /**
     * 获取 部门ID
     *
     * @return id 部门ID
     */
    public Long getId() {
        return this.id;
    }

    /**
     * 设置 部门ID
     *
     * @param id 部门ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取 部门名称
     *
     * @return deptName 部门名称
     */
    public String getDeptName() {
        return this.deptName;
    }

    /**
     * 设置 部门名称
     *
     * @param deptName 部门名称
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * 获取 上级部门ID
     *
     * @return parentDeptId 上级部门ID
     */
    public Long getParentDeptId() {
        return this.parentDeptId;
    }

    /**
     * 设置 上级部门ID
     *
     * @param parentDeptId 上级部门ID
     */
    public void setParentDeptId(Long parentDeptId) {
        this.parentDeptId = parentDeptId;
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
