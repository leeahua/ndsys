package com.hexin.user.vo;

/**
 * 分页模型
 * @author 
 *
 */
public class PageVO {

	private int page; // 第几页
	private int pageSize; // 每页记录数


	public PageVO(int page, int pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
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
		return (page-1)*pageSize;
	}
	
	
}
