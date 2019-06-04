package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;

public interface TbContentService {
	/**
	 * 根据类别查出当前类别下所有的内容
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid page(long categoryId,int page,int rows);
	
	/**
	 * 新增content
	 */
	int insContent(TbContent content) throws Exception;
	
	/**
	 * 修改tbContent
	 */
	int updContent(TbContent content);
	
	/**
	 * 删除tbCOntent
	 */
	int delContent(String string);

}
