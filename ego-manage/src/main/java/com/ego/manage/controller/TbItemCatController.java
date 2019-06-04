package com.ego.manage.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUITree;
import com.ego.manage.service.TbItemCatService;

@Controller
public class TbItemCatController {
	@Resource
	private TbItemCatService tbItemCatServiceImpl;
	
	@RequestMapping("item/cat/list")
	@ResponseBody
	public List<EasyUITree> show(@RequestParam(defaultValue="0") long id){
		List<EasyUITree> selByPid = tbItemCatServiceImpl.selByPid(id);
		return selByPid;
	}
}
