package com.ego.dubbo.service;


import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItemParam;

public interface TbItemParamDubboService {
	EasyUIDataGrid selpage(int page,int rows);
	
	/**
	 * 根据id删除指定描述类
	 */
	int delById(String ids) throws Exception;
	
	
	/**
	 * 根据catid找出信息
	 */
	TbItemParam selById(long catid);
	
	/**
	 * 新增商品类目信息
	 */
	int insParam(TbItemParam tbItemParam) throws Exception;
}
