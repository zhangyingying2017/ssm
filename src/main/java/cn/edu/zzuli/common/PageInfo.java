package cn.edu.zzuli.common;

public class PageInfo {
	/**
	 * 当前页，1基址
	 */
	private Integer page = SysContants.PAGE_SIZE;
	/**
	 * 每页记录数,10
	 */
	private Integer rows = SysContants.PAGE_ROWS;
	
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}

}
