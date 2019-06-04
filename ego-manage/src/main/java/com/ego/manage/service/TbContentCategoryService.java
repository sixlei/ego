package com.ego.manage.service;

import java.util.List;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

public interface TbContentCategoryService {
	List<EasyUITree> selByPid(long id);
	
	/**
	 * 新增
	 */
	EgoResult insCate(TbContentCategory category) throws Exception;
	
	/**
	 * 修改名称
	 */
	EgoResult update(TbContentCategory category);
	
	/**
	 * 删除节点
	 */
	EgoResult delete(TbContentCategory category);


}
