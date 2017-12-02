package com.hexin.user.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by liyaohua on 2017-10-25.
*/
public class PigWidth implements Serializable{
        // id
        private Integer id;
        // 批次号
        private String pigBatchNo;
        // 序号
        private Integer pigNum;
        // 猪肉等级
        private String pigLevel;
        //宽度
        private BigDecimal pigWidth;
        //重量
        private BigDecimal pigWeight;
        // 颜色
        private String pigColor;
        // 负责人
        private String chargeMan;
        // 创建时间
        private Date createTime;
        // 更新时间
        private Date updateTime;


        public Integer getId() {
            return id;
        }

        public PigWidth setId(Integer id) {
            this.id = id;
            return this;
        }
        public String getPigBatchNo() {
            return pigBatchNo;
        }

        public void setPigBatchNo(String pigBatchNo) {
            this.pigBatchNo = pigBatchNo;
        }
        public Integer getPigNum() {
            return pigNum;
        }

        public void setPigNum(Integer pigNum) {
            this.pigNum = pigNum;
        }
        public String getPigColor() {
            return pigColor;
        }

        public void setPigColor(String pigColor) {
            this.pigColor = pigColor;
        }
        public String getChargeMan() {
            return chargeMan;
        }

        public void setChargeMan(String chargeMan) {
            this.chargeMan = chargeMan;
        }
        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }
        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }
        public String getPigLevel() {
            return pigLevel;
        }

        public void setPigLevel(String pigLevel) {
            this.pigLevel = pigLevel;
        }

    @Override
    public String toString() {
        return "PigWidth{" +
                "id=" + id +
                ", pigBatchNo='" + pigBatchNo + '\'' +
                ", pigNum=" + pigNum +
                ", pigColor='" + pigColor + '\'' +
                ", chargeMan='" + chargeMan + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", pigLevel='" + pigLevel + '\'' +
                '}';
    }

    public BigDecimal getPigWidth() {
        return pigWidth;
    }

    public void setPigWidth(BigDecimal pigWidth) {
        this.pigWidth = pigWidth;
    }

    public BigDecimal getPigWeight() {
        return pigWeight;
    }

    public void setPigWeight(BigDecimal pigWeight) {
        this.pigWeight = pigWeight;
    }
}
