package com.hexin.user.model;

import java.io.Serializable;
import java.util.Date;

public class PigLevel implements Serializable {
    private Integer id;

    private Double level1;

    private Double level2;

    private Double level3;

    private Double level4;

    private Double level5;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLevel1() {
        return level1;
    }

    public void setLevel1(Double level1) {
        this.level1 = level1;
    }

    public Double getLevel2() {
        return level2;
    }

    public void setLevel2(Double level2) {
        this.level2 = level2;
    }

    public Double getLevel3() {
        return level3;
    }

    public void setLevel3(Double level3) {
        this.level3 = level3;
    }

    public Double getLevel4() {
        return level4;
    }

    public void setLevel4(Double level4) {
        this.level4 = level4;
    }

    public Double getLevel5() {
        return level5;
    }

    public void setLevel5(Double level5) {
        this.level5 = level5;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}