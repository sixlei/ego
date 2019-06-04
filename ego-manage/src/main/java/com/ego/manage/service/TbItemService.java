package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;

public interface TbItemService {
	EasyUIDataGrid show(int page,int rows);
	
	/**
	 * 修改商品状态
	 */
	int updStus(String ids,byte status);
	
	int save(TbItem tbItem,String desc,String tbItemString) throws Exception;
}
