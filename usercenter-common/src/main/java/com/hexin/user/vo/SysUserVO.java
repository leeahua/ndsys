package com.hexin.user.vo;

import com.hexin.user.model.SysUser;

/**
 * Created on IDEA.
 * User: lyh
 * Date: 2017/10/29
 * Time: 20:09
 */
public class SysUserVO extends SysUser {
    private int page; // 第几页
    private int pageSize; // 每页记录数
    private int start;

    public void setStart(int start) {
        this.start = start;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return (this.page-1)*this.pageSize;
    }
}
