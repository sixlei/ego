package com.ego.dubbo.service;

import java.util.List;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;

public interface TbContentDubboService {
	
	EasyUIDataGrid page(long categoryId,int page,int rows);
	
	/**
	 * 新增content
	 */
	int insContent(TbContent content);
	
	/**
	 *查询前多少个广告
	 */
	List<TbContent> selCount(int count,boolean sorted);
	
	/**
	 * 修改tbContent
	 */
	int updContent(TbContent content);
	
	/**
	 * 删除tbContent
	 */
	int delContent(long id);

}
