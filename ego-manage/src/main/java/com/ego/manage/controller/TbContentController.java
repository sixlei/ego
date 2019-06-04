package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;

@Controller
public class TbContentController {
	@Resource
	private TbContentService tbContentServiceImpl;
	
	@RequestMapping("content/query/list")
	@ResponseBody
	public EasyUIDataGrid show(long categoryId,int page,int rows){
		EasyUIDataGrid grid = tbContentServiceImpl.page(categoryId, page, rows);
		return grid;
	}
	
	@RequestMapping("content/save")
	@ResponseBody
	public EgoResult save(TbContent content){
		EgoResult egoResult = new EgoResult();
		
		try {
			tbContentServiceImpl.insContent(content);
			egoResult.setStatus(200);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			egoResult.setMessage(e.getMessage());
		}
		return egoResult;
	}
	
	@RequestMapping("rest/content/edit")
	@ResponseBody
	public EgoResult update(TbContent content){
		EgoResult er=new EgoResult();
		int index = tbContentServiceImpl.updContent(content);
		if (index>0) {
			er.setStatus(200);
		}
		return er;
	}
	
	@RequestMapping("content/delete")
	@ResponseBody
	public EgoResult delete(String ids){
		EgoResult egoResult = new EgoResult();
		int index = tbContentServiceImpl.delContent(ids);
		if (index>0) {
			egoResult.setStatus(200);
		}
		return egoResult;
	}
}
