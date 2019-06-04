package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;

@Controller
public class TbItemController {
	@Resource
	private TbItemService tbItemServiceImplItem;
	
	@RequestMapping("item/list")
	@ResponseBody
	public EasyUIDataGrid show(int page,int rows){
		EasyUIDataGrid show = tbItemServiceImplItem.show(page, rows);
		return show;
	}
	
	
	/**
	 * 返回状态码,所以应该再commoms中新建一个状态码类
	 */
	@RequestMapping("rest/item/instock")
	@ResponseBody
	public EgoResult downItem(String ids) {
		int updStus = tbItemServiceImplItem.updStus(ids, (byte)2);
		if (updStus==1) {
			EgoResult egoResult = new EgoResult();
			egoResult.setStatus(200);
			return egoResult;
		}
		return null;
	}
	
	@RequestMapping("rest/item/reshelf")
	@ResponseBody
	public EgoResult upItem(String ids){
		int updStus = tbItemServiceImplItem.updStus(ids, (byte)1);
		if (updStus==1) {
			EgoResult egoResult = new EgoResult();
			egoResult.setStatus(200);
			return egoResult;
		}
		return null;
	}
	
	@RequestMapping("rest/item/delete")
	@ResponseBody
	public EgoResult delete(String ids){
		int updStus = tbItemServiceImplItem.updStus(ids, (byte)3);
		if (updStus==1) {
			EgoResult egoResult = new EgoResult();
			egoResult.setStatus(200);
			return egoResult;
		}
		return null;
	}
	
	@RequestMapping("item/save")
	@ResponseBody
	public EgoResult save(TbItem item,String desc,String itemParams){
		EgoResult egoResult = new EgoResult();
		try {
			int index = tbItemServiceImplItem.save(item, desc,itemParams);
			if(index==1){
				egoResult.setStatus(200);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			egoResult.setMessage(e.getMessage());
		}
		return egoResult;
		
	}
	
	
}
