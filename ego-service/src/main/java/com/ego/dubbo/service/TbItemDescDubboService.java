package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

public interface TbItemDescDubboService {
	/**
	 * 根据商品id查出商品的描述信息
	 * @param id
	 * @return
	 */
	TbItemDesc seldescById(long id);

}
