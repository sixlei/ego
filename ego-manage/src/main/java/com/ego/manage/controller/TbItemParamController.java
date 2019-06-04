package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;

@Controller
public class TbItemParamController {
	@Resource
	private TbItemParamService tbItemParamServiceImpl;
	
	@RequestMapping("item/param/list")
	@ResponseBody
	public EasyUIDataGrid show(int page,int rows){
		EasyUIDataGrid show = tbItemParamServiceImpl.show(page, rows);
		return show;
	}
	
	@RequestMapping("item/param/delete")
	@ResponseBody
	public EgoResult delete(String ids) {
		EgoResult egoResult = new EgoResult();
		int result;
		try {
			result = tbItemParamServiceImpl.delById(ids);
			if (result==1) {
				egoResult.setStatus(200);
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			egoResult.setMessage(e.getMessage());
		}
		
		return egoResult;
		
	}
	
	@RequestMapping("item/param/query/itemcatid/{catId}")
	@ResponseBody
	public EgoResult show(@PathVariable long catId){
		EgoResult result = tbItemParamServiceImpl.selById(catId);
		return result;
		
	}
	
	@RequestMapping("item/param/save/{catId}")
	@ResponseBody
	public EgoResult save(TbItemParam tbItemParam,@PathVariable long catId){
		tbItemParam.setItemCatId(catId);
		EgoResult result = tbItemParamServiceImpl.insTbItemParam(tbItemParam);
		return result;
		
	}
	
}
