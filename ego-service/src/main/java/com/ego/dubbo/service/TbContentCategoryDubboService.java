package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbContentCategory;

public interface TbContentCategoryDubboService {
	List<TbContentCategory> selByPid(long id);
	
	TbContentCategory selById(long id);
	
	/**
	 * 新增一个表项
	 */
	int insCategory(TbContentCategory category);
	
	/**
	 * 将原来的子节点修改为父节点
	 */
	int updCategory(TbContentCategory category);
	
	/**
	 * 重命名功能
	 * @param category
	 * @return
	 */
	int update(TbContentCategory category);
	
	/**
	 * 删除节点（修改当前id的状态为2）
	 */
	int delete(TbContentCategory category);

}
