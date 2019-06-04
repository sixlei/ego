package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamChild;
@Service
public class TbItemParamServiceImpl implements TbItemParamService{
	@Reference
	private TbItemParamDubboService tbItemParamDubboServiceImpl;
	
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;

	@Override
	public EasyUIDataGrid show(int page, int rows) {
		EasyUIDataGrid selpage = tbItemParamDubboServiceImpl.selpage(page, rows);
		List<TbItemParam> tbItemParams = (List<TbItemParam>) selpage.getRows();
		ArrayList<TbItemParamChild> childs = new ArrayList<>();
		for (TbItemParam tbItemParam : tbItemParams) {
			TbItemParamChild child = new TbItemParamChild();
			child.setCreated(tbItemParam.getCreated());			
			child.setId(tbItemParam.getId());
			child.setParamData(tbItemParam.getParamData());
			child.setUpdated(tbItemParam.getUpdated());
			child.setItemCatId(tbItemParam.getItemCatId());
			child.setItemCatName(tbItemCatDubboServiceImpl.selNameById(tbItemParam.getItemCatId()).getName());
			childs.add(child);
		}
		selpage.setRows(childs);
		
		return selpage;
	}

	@Override
	public int delById(String idsString) throws Exception {
		int result = tbItemParamDubboServiceImpl.delById(idsString);
		return result;
	}

	@Override
	public EgoResult selById(long catid) {
		TbItemParam selById = tbItemParamDubboServiceImpl.selById(catid);
		EgoResult egoResult = new EgoResult();
		
		if (selById!=null) {
			egoResult.setStatus(200);
			egoResult.setData(selById);
			return egoResult;
		}
		// TODO Auto-generated method stub
		return egoResult;
	}
	
	/**
	 * 新增
	 */
	@Override
	public EgoResult insTbItemParam(TbItemParam tbItemParam) {
		Date date = new Date();
		tbItemParam.setCreated(date);
		tbItemParam.setUpdated(date);
		EgoResult egoResult = new EgoResult();
		try {
			int insParam = tbItemParamDubboServiceImpl.insParam(tbItemParam);
			if (insParam>0) {
				egoResult.setStatus(200);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			egoResult.setData(e.getMessage());
		}
		// TODO Auto-generated method stub
		return egoResult;
	}



}
