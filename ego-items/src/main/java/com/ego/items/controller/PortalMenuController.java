package com.ego.items.controller;

import javax.annotation.Resource;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.items.pojo.PortalMenu;
import com.ego.items.service.TbItemCatService;

@Controller
public class PortalMenuController {
	@Resource
	private TbItemCatService tbItemCatServiceImpl;
	
	@RequestMapping("rest/itemcat/all")
	@ResponseBody
	public MappingJacksonValue show(String callback){
		PortalMenu portalMenu = tbItemCatServiceImpl.selAllMenu();
		MappingJacksonValue mjv = new MappingJacksonValue(portalMenu);
		mjv.setJsonpFunction(callback);
		return mjv;
	}
}
