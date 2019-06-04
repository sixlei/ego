package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbContentDubboServiceImpl implements TbContentDubboService{
	@Resource 
	private TbContentMapper tbContentMapper;

	@Override
	public EasyUIDataGrid page(long categoryId, int page, int rows) {
		EasyUIDataGrid grid = new EasyUIDataGrid();
		//开启分页功能
		PageHelper.startPage(page,rows);
		//根据条件查询所有
		TbContentExample example = new TbContentExample();
		if (categoryId!=0) {
			example.createCriteria().andCategoryIdEqualTo(categoryId);
		}
		List<TbContent> list = tbContentMapper.selectByExample(example);
		
		PageInfo<TbContent> pInfo = new PageInfo<>(list);
		// TODO Auto-generated method stub
		grid.setRows(pInfo.getList());
		grid.setTotal(pInfo.getTotal());
		return grid;
	}

	@Override
	public int insContent(TbContent content) {
		int index = tbContentMapper.insertSelective(content);
		return index;
	}
	
	/**
	 * 查询前多少个广告，加上sorted的理由是为了让广告排序，可以让该方法供那些不需要排序的查询使用
	 */
	@Override
	public List<TbContent> selCount(int count, boolean sorted) {
		
		TbContentExample example = new TbContentExample();
		
		if (sorted) {
			example.setOrderByClause("updated desc");
		}
		
		
		if (count!=0) {
			//可以使用分页查询前多少个
			PageHelper.startPage(1, count);
			List<TbContent> list = tbContentMapper.selectByExample(example);
			PageInfo<TbContent> pi = new PageInfo<>(list);
			return pi.getList();
		}
		else {
			//如果没设置查询数量就查询出全部
			return tbContentMapper.selectByExample(example);
		}
	}

	@Override
	public int updContent(TbContent content) {
		int index = tbContentMapper.updateByPrimaryKeySelective(content);
		
		return index;
	}

	@Override
	public int delContent(long id) {
		int index = tbContentMapper.deleteByPrimaryKey(id);
		// TODO Auto-generated method stub
		return index;
	}

}
