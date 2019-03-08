package cn.sambo.difference.platform.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
@DynamicUpdate(true)
@DynamicInsert(true)
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    private String createdBy;
    private String updatedBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp lastUpdateTime;


    /**
     * 获取 @Id    @GeneratedValue
     *
     * @return id @Id    @GeneratedValue
     */
    public Long getId() {
        return this.id;
    }

    /**
     * 设置 @Id    @GeneratedValue
     *
     * @param id @Id    @GeneratedValue
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取
     *
     * @return createdBy
     */
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 设置
     *
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获取
     *
     * @return updatedBy
     */
    public String getUpdatedBy() {
        return this.updatedBy;
    }

    /**
     * 设置
     *
     * @param updatedBy
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * 获取
     *
     * @return createdTime
     */
    public Timestamp getCreatedTime() {
        return this.createdTime;
    }

    /**
     * 设置
     *
     * @param createdTime
     */
    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 获取
     *
     * @return lastUpdateTime
     */
    public Timestamp getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    /**
     * 设置
     *
     * @param lastUpdateTime
     */
    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
