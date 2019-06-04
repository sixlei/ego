package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemParam;

public interface TbItemParamService {
	EasyUIDataGrid show(int page,int rows);
	
	int delById(String idsString) throws Exception;
	
	EgoResult selById(long catid);
	
	EgoResult insTbItemParam(TbItemParam tbItemParam);
}
