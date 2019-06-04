package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

public interface TbOrderDubboService {
	/**
	 * 根据接收到的tborder,tborderitem.tbordershipping将其插入到对应的表格中
	 */
	int insTbOrder(TbOrder tbOrder,List<TbOrderItem> tbOrderItems,TbOrderShipping tbOrderShipping) throws Exception;

}
