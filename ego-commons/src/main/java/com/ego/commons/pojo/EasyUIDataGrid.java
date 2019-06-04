package com.ego.commons.pojo;

import java.io.Serializable;
import java.util.List;


public class EasyUIDataGrid implements Serializable{
	//显示真实数据
	List<?> rows;
	//总条数
	Long total;
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}

}
