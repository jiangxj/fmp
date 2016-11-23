package com.luckymiao.common.mybatis.plugin;

/**
 * 分页参数类
 * 
 */
public class PageParameter {

	public static final int DEFAULT_PAGE_SIZE = 10;

	private int pageSize;
	private int intPage;
	private int totalPage;
	private int totalCount;

	public PageParameter() {
		this.intPage = 1;
		this.pageSize = DEFAULT_PAGE_SIZE;
	}

	/**
	 * 
	 * @param currentPage
	 * @param pageSize
	 */
	public PageParameter(int intPage, int pageSize) {
		this.intPage = intPage;
		this.pageSize = pageSize;
	}

	public int getIntPage() {
		return intPage;
	}

	public void setIntPage(int intPage) {
		this.intPage = intPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

}
