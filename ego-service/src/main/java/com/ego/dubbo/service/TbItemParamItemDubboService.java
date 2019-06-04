package com.ego.dubbo.service;

import com.ego.pojo.TbItemParamItem;

public interface TbItemParamItemDubboService {
	
	/**
	 * 根据id查询出物品的规格参数
	 */
	TbItemParamItem selById(long id);

}
