package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItemParamItem;
import com.ego.pojo.TbItemParamItemExample;

public class TbItemParamItemDubboServiceImpl implements TbItemParamItemDubboService{
	
	@Resource
	private TbItemParamItemMapper tbItemParamItemMapper;

	@Override
	public TbItemParamItem selById(long id) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		example.createCriteria().andItemIdEqualTo(id);
		//因为要查询出text类型的数据，所以用BLOBs
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list!=null&&list.size()>0) {
			return list.get(0);
			
		}
		return null;
	}

}
