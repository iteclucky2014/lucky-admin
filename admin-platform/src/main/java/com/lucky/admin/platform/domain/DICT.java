package com.lucky.admin.platform.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@DynamicUpdate(true)
@DynamicInsert(true)
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@Table(name = "BIZ_DICT")
public class DICT extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 字典值 */
    private String dictCode;
    /** 字典名 */
    private String dictName;
    /** 字典数据值 */
    private String dictDataCode;
    /** 字典数据名 */
    private String dictDataText;
    /** 显示顺序 */
    private Integer dispOrder;
    /** 使用中 */
    private Integer useFlag;
    /**
     * 获取 字典值
     *
     * @return dictCode 字典值
     */
    public String getDictCode() {
        return this.dictCode;
    }

    /**
     * 设置 字典值
     *
     * @param dictCode 字典值
     */
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    /**
     * 获取 字典名
     *
     * @return dictName 字典名
     */
    public String getDictName() {
        return this.dictName;
    }

    /**
     * 设置 字典名
     *
     * @param dictName 字典名
     */
    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    /**
     * 获取 字典数据值
     *
     * @return dictDataCode 字典数据值
     */
    public String getDictDataCode() {
        return this.dictDataCode;
    }

    /**
     * 设置 字典数据值
     *
     * @param dictDataCode 字典数据值
     */
    public void setDictDataCode(String dictDataCode) {
        this.dictDataCode = dictDataCode;
    }

    /**
     * 获取 字典数据名
     *
     * @return dictDataText 字典数据名
     */
    public String getDictDataText() {
        return this.dictDataText;
    }

    /**
     * 设置 字典数据名
     *
     * @param dictDataText 字典数据名
     */
    public void setDictDataText(String dictDataText) {
        this.dictDataText = dictDataText;
    }

    /**
     * 获取 显示顺序
     *
     * @return dispOrder 显示顺序
     */
    public Integer getDispOrder() {
        return this.dispOrder;
    }

    /**
     * 设置 显示顺序
     *
     * @param dispOrder 显示顺序
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

}