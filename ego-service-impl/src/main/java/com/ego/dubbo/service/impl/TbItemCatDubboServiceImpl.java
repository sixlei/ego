package com.ego.dubbo.service.impl;

import java.util.List;







import javax.annotation.Resource;

import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.mapper.TbItemCatMapper;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;
import com.ego.pojo.TbItemParam;

public class TbItemCatDubboServiceImpl implements TbItemCatDubboService{
	@Resource 
	private TbItemCatMapper tbItemCatMapper;

	@Override
	public List<TbItemCat> show(long pid) {
		TbItemCatExample tbItemCatExample =new TbItemCatExample();
		tbItemCatExample.createCriteria().andParentIdEqualTo(pid);
		List<TbItemCat> selectByExample = tbItemCatMapper.selectByExample(tbItemCatExample);
		// TODO Auto-generated method stub
		return selectByExample;
	}

	@Override
	public TbItemCat selNameById(long id) {
		TbItemCat selectByPrimaryKey = tbItemCatMapper.selectByPrimaryKey(id);
		// TODO Auto-generated method stub
		return selectByPrimaryKey;
	}

	

}
