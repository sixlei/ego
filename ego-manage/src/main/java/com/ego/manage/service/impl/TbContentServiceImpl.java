package com.ego.manage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import com.ego.redis.dao.JedisDao;
@Service
public class TbContentServiceImpl implements TbContentService{
	@Reference
	private TbContentDubboService tbContentDubboServiceImpl;
	
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${redis.bigPic.key}")
	private String key;

	@Override
	public EasyUIDataGrid page(long categoryId, int page, int rows) {
		EasyUIDataGrid grid = tbContentDubboServiceImpl.page(categoryId, page, rows);
		// TODO Auto-generated method stub
		return grid;
	}
	//新增
	@Override
	public int insContent(TbContent content) throws Exception {
		Date date = new Date();
		content.setUpdated(date);
		content.setCreated(date);
		int index = tbContentDubboServiceImpl.insContent(content);
		if (index>0) {
			
			/**
			 * 将content的数据放入到redis缓存中
			 */
			if (jedisDaoImpl.exists(key)) {
				String value = jedisDaoImpl.get(key);
				List<HashMap> list = JsonUtils.jsonToList(value, HashMap.class);
				
				HashMap<String, Object> map=new HashMap<String, Object>();
				map.put("srcB", content.getPic2());
				map.put("height", 240);
				map.put("alt","图片加载失败" );
				map.put("width", 670);
				map.put("src", content.getPic());
				map.put("widthB", 550);
				map.put("href", content.getUrl());
				
				
				if (list.size()==6) {
					list.remove(5);
				}
				list.add(0, map);
				jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
			}
			
			
			return index;
			
		}else {
			throw new Exception("新增失败");
		}
	
	}
	@Override
	public int updContent(TbContent content) {
		Date date = new Date();
		content.setUpdated(date);
		
		int index = tbContentDubboServiceImpl.updContent(content);
		
		//判断是不是在redis中，如果再redis中，就先将redis里面的改掉
//		String string = jedisDaoImpl.get(key);
//		List<HashMap> pics = JsonUtils.jsonToList(string, HashMap.class);
//		for (HashMap map : pics) {
//			
//		}
		
		return index;
	}
	@Override
	public int delContent(String string) {
		String[] ids = string.split(",");
		int index=0;
		for (String id : ids) {
			index+=tbContentDubboServiceImpl.delContent(Long.parseLong(id));
		}
		if (index==ids.length) {
			return 1;
		}
		return 0;
	}
	
	

}
