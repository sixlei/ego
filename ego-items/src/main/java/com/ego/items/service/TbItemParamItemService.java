package com.ego.items.service;

public interface TbItemParamItemService {
	/**
	 * 根据商品id查询出商品的规格参数信息
	 * 完成缓存到redis的功能
	 * @param id
	 * @return
	 */
	String selById(long id);

}
