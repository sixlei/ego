package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.List;




import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUITree;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.manage.service.TbItemCatService;
import com.ego.pojo.TbItemCat;
@Service
public class TbItemCatSerciceImpl implements TbItemCatService{
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;

	@Override
	public List<EasyUITree> selByPid(long pid) {
		List<TbItemCat> lists = tbItemCatDubboServiceImpl.show(pid);
		ArrayList<EasyUITree> tree = new ArrayList<EasyUITree>();
		for (TbItemCat tbItemCat : lists) {
			EasyUITree easyUITree = new EasyUITree();
			easyUITree.setId(tbItemCat.getId());
			easyUITree.setText(tbItemCat.getName());
			easyUITree.setState(tbItemCat.getIsParent()?"closed":"open");
			tree.add(easyUITree);
			
		}
		
		// TODO Auto-generated method stub
		return tree;
	}

}
