package com.ego.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.portal.service.TbContentService;
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
	public String selByCount() {
		
		//先查询redis是不是为空
		if (jedisDaoImpl.exists(key)) {
			
			String value = jedisDaoImpl.get(key);
			if (value!=null&&!value.equals("")) {
				return value;
			}
		}
		
		
		//redis为空的时候
		//查询出要显示的内容
		List<TbContent> list = tbContentDubboServiceImpl.selCount(6, true);
		//进行封装,[{a:b},{c:d}],应该是一个列表里面有很多map
		List<Map<String, Object>> result=new ArrayList<>();
		
		for (TbContent tc:list) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("srcB", tc.getPic2());
			map.put("height", 240);
			map.put("alt","图片加载失败" );
			map.put("width", 670);
			map.put("src", tc.getPic());
			map.put("widthB", 550);
			map.put("href", tc.getUrl());
			result.add(map);
		}
		String value = JsonUtils.objectToJson(result);
		jedisDaoImpl.set(key, value);
		return value;
	}
	
	
	
}
