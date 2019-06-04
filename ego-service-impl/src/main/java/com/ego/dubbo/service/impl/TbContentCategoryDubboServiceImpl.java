package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;
/**
 * 使用easyui菜单的步骤
 * 1.在dubbo服务中根据pid查出父菜单为pid的所有菜单
 * 2.在manage的service中根据查出来的菜单组装成easyui的tree的格式，传给easyui
 * @author leilei
 *
 */

public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService{
	
	@Resource
	private TbContentCategoryMapper tbContentCategoryMapper;

	@Override
	public List<TbContentCategory> selByPid(long id) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(id).andStatusEqualTo(1);
		List<TbContentCategory> selectByExample = tbContentCategoryMapper.selectByExample(example);
		
		// TODO Auto-generated method stub
		return selectByExample;
	}

	@Override
	public int insCategory(TbContentCategory category) {
		
		return tbContentCategoryMapper.insertSelective(category);
	}

	@Override
	public int updCategory(TbContentCategory category) {
		return tbContentCategoryMapper.updateByPrimaryKeySelective(category);
	}

	@Override
	public TbContentCategory selById(long id) {
		TbContentCategory category = tbContentCategoryMapper.selectByPrimaryKey(id);
		return category;
	}

	@Override
	public int update(TbContentCategory category) {
		//得到要修改的全部信息
		TbContentCategory cate = selById(category.getId());
		
		//根据全部信息查出当前父节点的所有节点
		List<TbContentCategory> lists = selByPid(cate.getParentId());
		for (TbContentCategory tbContentCategory : lists) {
			if (tbContentCategory.getName().equals(category.getName())) {
				return 0;
			}
		}
		
		int update = tbContentCategoryMapper.updateByPrimaryKeySelective(category);
		System.out.println(update);
		
		return update;
	}
	
	
	//删除
	@Override
	public int delete(TbContentCategory category) {
		//根据当前id查询出完整信息，根据此信息查询父节点当前的子节点有几个
		TbContentCategory cate = selById(category.getId());
		//如果当前父节点下只有一个子节点，那就将父节点的isparent改成false
		List<TbContentCategory> lists = selByPid(cate.getParentId());
		if (lists.size()==1) {
			TbContentCategory parent = new TbContentCategory();
			parent.setId(cate.getParentId());
			parent.setIsParent(false);
			updCategory(parent);
		}
		category.setStatus(2);
		int delete = update(category);
		return delete;
	}
	

}
