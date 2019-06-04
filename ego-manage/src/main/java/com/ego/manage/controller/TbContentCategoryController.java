package com.ego.manage.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;

@Controller
public class TbContentCategoryController {
	@Resource
	private TbContentCategoryService tbContentCategoryServiceImpl;
	
	@RequestMapping("content/category/list")
	@ResponseBody
	public List<EasyUITree> show(@RequestParam(defaultValue="0")long id){
		List<EasyUITree> selByPid = tbContentCategoryServiceImpl.selByPid(id);
		return selByPid;
		
	}
	
	@RequestMapping("content/category/create")
	@ResponseBody
	public EgoResult insCate(TbContentCategory category){
		EgoResult result = new EgoResult();
		try {
			EgoResult insCate = tbContentCategoryServiceImpl.insCate(category);
			return insCate;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			result.setData(e.getMessage());
			return result;
		}
	}
	
	@RequestMapping("content/category/update")
	@ResponseBody
	public EgoResult update(TbContentCategory category){
		EgoResult update = tbContentCategoryServiceImpl.update(category);
		return update;
	}
	
	@RequestMapping("content/category/delete/")
	@ResponseBody
	public EgoResult delete(TbContentCategory category){
		EgoResult delete = tbContentCategoryServiceImpl.delete(category);
		return delete;
	}
}
