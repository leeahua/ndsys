package com.hexin.user.model;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
* Created by liyaohua on 2017-10-25.
*/
public class SysUser implements Serializable{
    // 主键ID
    private Integer id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 角色
    private String realname;
    // 状态
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realname='" + realname + '\'' +
                ", status=" + status +
                '}';
    }


}
