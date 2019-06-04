package com.ego.cart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemChild;

public interface TbItemCartService {
	
	/**
	 * 根据id和数量将物品信息对应的username放入到redis数据库中
	 */
	void insRedis(long id,int num,HttpServletRequest request);
	
	/**
	 * 购物车显示商品信息
	 */
	List<TbItemChild> selitems(HttpServletRequest request);
	
	
	/**
	 * 修改购物车商品数量
	 */
	EgoResult updNums(long id,int num,HttpServletRequest request);
	
	/**
	 * 删除购物车指定内容
	 */
	EgoResult delItems(long id,HttpServletRequest request);

}
