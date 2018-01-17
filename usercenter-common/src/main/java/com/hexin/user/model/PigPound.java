package com.hexin.user.model;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
* Created by liyaohua on 2017-10-25.
*/
public class PigPound implements Serializable{
    // id
    private Integer id;
    // 底磅重量
    private Double botpounds;

    // 批次号
    private String batchNum;
    // 创建时间
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public Double getBotpounds() {
        return botpounds;
    }

    public void setBotpounds(Double botpounds) {
        this.botpounds = botpounds;
    }
}
