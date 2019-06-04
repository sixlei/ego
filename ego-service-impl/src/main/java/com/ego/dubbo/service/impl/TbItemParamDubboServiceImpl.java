package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;








import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemParamDubboServiceImpl implements TbItemParamDubboService{
	@Resource
	private TbItemParamMapper tbItemParamMapper;

	@Override
	public EasyUIDataGrid selpage(int page, int rows) {
		
		
		//开启分页插件功能
		PageHelper.startPage(page, rows);
		//查询全部
		//查询全部时，如果在数据类型有text类型，那么应该使用xxxxwithBLOBs()方法，因为查询text的效率较低。如果每次都是用查询text，会降低效率，所以应该另外使用xxxwithBLOBs方法
		//List<TbItemParam> lists = tbItemParamMapper.selectByExample(new TbItemParamExample());
		List<TbItemParam> lists = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
		//查询limit的内容
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(lists);
		//根据内容组装成easyui的内容
		EasyUIDataGrid uid = new EasyUIDataGrid();
		uid.setRows(pageInfo.getList());
		uid.setTotal(pageInfo.getTotal());
		
		
		// TODO Auto-generated method stub
		return uid;
	}
	
	
	/**
	 * 根据id删除
	 * @throws Exception 
	 */
	@Override
	public int delById(String ids) throws Exception {
		String[] pids = ids.split(",");
		int index=0;
		for (String id : pids) {
			
			index += tbItemParamMapper.deleteByPrimaryKey(Long.parseLong(id));
		}
		if (index==pids.length) {
			return 1;
		}else {
			throw new Exception("删除失败");
		}
	}


	@Override
	public TbItemParam selById(long catid) {
		TbItemParamExample example = new TbItemParamExample();
		example.createCriteria().andItemCatIdEqualTo(catid);
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		if (list!=null&&list.size()>0) {
			return list.get(0);
			
		}
		
		return null;
	}


	@Override
	public int insParam(TbItemParam tbItemParam) throws Exception {
		int insertSelective = tbItemParamMapper.insertSelective(tbItemParam);
		if (insertSelective>0) {
			return 1;
		}else {
			throw new Exception("新增失败");
		}
	}

}
