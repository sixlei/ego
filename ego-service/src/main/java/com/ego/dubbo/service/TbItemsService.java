package com.ego.dubbo.service;


import java.util.List;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemChild;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

public interface TbItemsService {
	/**
	 * 查询分页功能
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid selByPages(int page,int rows);
	/**
	 * 修改商品状态，1正常，2下架，3删除
	 * @param tbItem
	 * @return
	 */
	int updStus(TbItem tbItem);
	/**
	 * 新加商品信息
	 * @param tbItem
	 * @param tbItemDesc
	 * @return
	 */
	int insTbItemDesc(TbItem tbItem,TbItemDesc tbItemDesc,TbItemParamItem paramItem) throws Exception;
	
	/**
	 * 查询所有商品
	 */
	List<TbItem> selAll(Byte status);
	
	
	/**
	 * 根据id查询出商品
	 */
	TbItem selById(long id);
}
