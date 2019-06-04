package com.ego.items.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.items.pojo.PortalMenu;
import com.ego.items.pojo.PortalMenuNode;
import com.ego.items.service.TbItemCatService;
import com.ego.pojo.TbItemCat;
@Service
public class TbItemCatServiceImpl implements TbItemCatService{
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;

	@Override
	public PortalMenu selAllMenu() {
		List<TbItemCat> list = tbItemCatDubboServiceImpl.show(0);
		PortalMenu pm = new PortalMenu();
		pm.setData(selByPid(list));
		return pm;
	}
	
	public List<Object> selByPid(List<TbItemCat> list) {
		List<Object> protalMenuNode =new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			if (tbItemCat.getIsParent()) {
				PortalMenuNode node = new PortalMenuNode();
				node.setU("/products/"+tbItemCat.getId()+".html");
				node.setN("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				node.setI(selByPid(tbItemCatDubboServiceImpl.show(tbItemCat.getId())));
				protalMenuNode.add(node);
			}else {
				protalMenuNode.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
			}
		}
		return protalMenuNode;
		
	}
	
	

}
