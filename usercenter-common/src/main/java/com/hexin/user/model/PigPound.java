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
    private BigDecimal botpounds;

    // 负责人
    private String chargeMan;
    // 创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public BigDecimal getBotpounds() {
        return botpounds;
    }

    public void setBotpounds(BigDecimal botpounds) {
        this.botpounds = botpounds;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getChargeMan() {
        return chargeMan;
    }

    public void setChargeMan(String chargeMan) {
        this.chargeMan = chargeMan;
    }
}
