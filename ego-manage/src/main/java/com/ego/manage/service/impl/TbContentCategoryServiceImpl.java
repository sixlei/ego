package com.ego.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;








import java.util.Map;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;
@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService{
	@Reference
	private TbContentCategoryDubboService tbContentCategoryDubboServiceImpl;

	@Override
	public List<EasyUITree> selByPid(long id) {
		List<EasyUITree> list=new ArrayList<EasyUITree>();
		List<TbContentCategory> contentCategories = tbContentCategoryDubboServiceImpl.selByPid(id);
		for (TbContentCategory tbContentCategory : contentCategories) {
			EasyUITree tree = new EasyUITree();
			tree.setId(tbContentCategory.getId());
			tree.setText(tbContentCategory.getName());
			tree.setState(tbContentCategory.getIsParent()?"closed":"open");
			list.add(tree);
			
		}
		return list;
	}

	@Override
	public EgoResult insCate(TbContentCategory category) throws Exception {
		EgoResult egoResult = new EgoResult();
		TbContentCategory parent = tbContentCategoryDubboServiceImpl.selById(category.getParentId());
		List<TbContentCategory> list = tbContentCategoryDubboServiceImpl.selByPid(category.getParentId());
		for (TbContentCategory tbContentCategory : list) {
			if (tbContentCategory.getName().equals(category.getName())) {
				egoResult.setData("已存在该标签");
				return egoResult;
			}
		}
			
		
		if(!parent.getIsParent()){
			parent.setIsParent(true);
			tbContentCategoryDubboServiceImpl.updCategory(parent);
		}
		
		Date date = new Date();
		long id = IDUtils.genItemId();
		category.setId(id);
		category.setCreated(date);
		category.setIsParent(false);
		category.setSortOrder(1);
		category.setStatus(1);
		category.setUpdated(date);
		
		int index = tbContentCategoryDubboServiceImpl.insCategory(category);
		if (index>0) {
			egoResult.setStatus(200);
			//这里可以直接把category传进去，但是如果把id做成一个map，效率会更高
			Map<String, Object> data=new HashMap<>();
			data.put("id", category.getId());
			egoResult.setData(data);
			return egoResult;
		}else {
			throw new Exception("新增失败");
		}
		
	}

	@Override
	public EgoResult update(TbContentCategory category) {
		EgoResult egoResult = new EgoResult();
		int update = tbContentCategoryDubboServiceImpl.update(category);
		if (update>0) {
			egoResult.setStatus(200); 
		}else {
			egoResult.setData("原因：已经存在该名称");
		}
		return egoResult;
	}

	@Override
	public EgoResult delete(TbContentCategory category) {
		EgoResult egoResult = new EgoResult();
		int delete = tbContentCategoryDubboServiceImpl.delete(category);
		if (delete>0) {
			egoResult.setStatus(200);
		}
		return egoResult;
	}
	

}
