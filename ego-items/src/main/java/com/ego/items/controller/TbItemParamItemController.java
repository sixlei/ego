package com.ego.items.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.items.service.TbItemParamItemService;

@Controller
public class TbItemParamItemController {
	@Resource
	private TbItemParamItemService tbItemParamItemServiceImpl;
	
	@RequestMapping(value="item/param/{id}.html",produces="text/html;charset=utf-8")
	@ResponseBody
	public String show(@PathVariable long id){
		
		String param = tbItemParamItemServiceImpl.selById(id);
		return param;
	}

}
