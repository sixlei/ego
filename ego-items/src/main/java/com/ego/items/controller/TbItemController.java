package com.ego.items.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ego.items.service.TbItemService;
import com.ego.pojo.TbItemChild;

@Controller
public class TbItemController {
	@Resource
	private TbItemService tbItemServiceImpl;
	
	
	@RequestMapping("item/{id}.html")
	public String itempage(@PathVariable long id,Model model){
		TbItemChild item = tbItemServiceImpl.selByid(id);
		model.addAttribute("item", item);
		return "item";
	}
}