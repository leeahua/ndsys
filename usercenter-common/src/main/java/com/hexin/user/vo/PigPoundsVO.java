package com.hexin.user.vo;

import com.hexin.user.model.PigPound;

import java.util.Date;

/**
 * Created on IDEA.
 * User: lyh
 * Date: 2017/10/29
 * Time: 11:18
 */
public class PigPoundsVO extends PigPound {

    private int page; // 第几页
    private int pageSize; // 每页记录数
    private int start;

    private Date startTime;

    private Date endTime;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
