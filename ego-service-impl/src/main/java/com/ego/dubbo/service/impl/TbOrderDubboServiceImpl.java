package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.mapper.TbOrderItemMapper;
import com.ego.mapper.TbOrderMapper;
import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

public class TbOrderDubboServiceImpl implements TbOrderDubboService{
	
	@Resource
	private TbOrderMapper tbOrderMapper;
	@Resource
	private TbOrderItemMapper tbOrderItemMapper;
	@Resource
	private TbOrderShippingMapper tbOrderShippingMapper;

	@Override
	public int insTbOrder(TbOrder tbOrder, List<TbOrderItem> tbOrderItems,TbOrderShipping tbOrderShipping) throws Exception {
		int index=0;
		index += tbOrderMapper.insertSelective(tbOrder);
		for (TbOrderItem tbOrderItem : tbOrderItems) {
			index+=tbOrderItemMapper.insertSelective(tbOrderItem);
		}
		index+=tbOrderShippingMapper.insertSelective(tbOrderShipping);
		if (index==2+tbOrderItems.size()) {
			return 1;
		}else {
			throw new Exception("添加失败");
		}
	}

}
