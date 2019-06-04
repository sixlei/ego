package com.ego.items.service;

public interface TbItemDescService {
	/**
	 * 根据id查询出商品描述信息,经过redis缓存
	 */
	
	String selDescById(long id);

}
