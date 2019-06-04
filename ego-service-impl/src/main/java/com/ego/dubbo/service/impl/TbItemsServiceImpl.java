package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.aspectj.weaver.IEclipseSourceContext;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemsService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemChild;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemExample;
import com.ego.pojo.TbItemParamItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.recompile;

public class TbItemsServiceImpl implements TbItemsService{
	@Resource
	private TbItemMapper tbItemMapper;
	@Resource 
	private TbItemDescMapper tbItemDescMapper;
	
	@Resource 
	private TbItemParamItemMapper tbItemParamItemMapper;

	@Override
	public EasyUIDataGrid selByPages(int page, int rows) {
		//分页代码
		//设置分页条件
		PageHelper.startPage(page, rows);   //要写在查询全部之前，不然没效果
		
		//查出所有结果集
		List<TbItem> allItems = tbItemMapper.selectByExample(new TbItemExample());

		//根据查询到的所有条数可以根据上一行的条件进行查询
		PageInfo<TbItem> pageInfo = new PageInfo<>(allItems);
		
		//将结果放入到实体类
		EasyUIDataGrid easyUIDataGrid = new EasyUIDataGrid();
		easyUIDataGrid.setRows(pageInfo.getList());
		easyUIDataGrid.setTotal(pageInfo.getTotal());
		
		
		return easyUIDataGrid;
	}

	@Override
	public int updStus(TbItem tbItem) {
		int updateByPrimaryKeySelective = tbItemMapper.updateByPrimaryKeySelective(tbItem);
		
		return updateByPrimaryKeySelective;
	}

	@Override
	public int insTbItemDesc(TbItem tbItem, TbItemDesc tbItemDesc,TbItemParamItem paramItem) throws Exception {
		int index=0;
		try {
			index+=tbItemMapper.insertSelective(tbItem);
			index+=tbItemDescMapper.insertSelective(tbItemDesc);
			index+= tbItemParamItemMapper.insertSelective(paramItem);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		if (index==3) {
			return 1;
		}else {
			throw new Exception("新增失败，数据还原");
		}
	}

	@Override
	public List<TbItem> selAll(Byte status) {
		TbItemExample example = new TbItemExample();
		example.createCriteria().andStatusEqualTo(status);
		List<TbItem> lists = tbItemMapper.selectByExample(example);
		
		return lists;
	}

	@Override
	public TbItem selById(long id) {
		return tbItemMapper.selectByPrimaryKey(id);
	}

}
