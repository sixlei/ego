package com.ego.items.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.items.service.TbItemDescService;
import com.ego.redis.dao.JedisDao;
@Service
public class TbItemDescServiceImpl implements TbItemDescService{
	
	@Reference
	private TbItemDescDubboService tbItemDescDubboServiceImpl;
	@Value("${redis.itemdesc.key=itemdesc:}")
	private String itemDesckey;
	@Resource
	private JedisDao jedisDaoImpl;

	@Override
	public String selDescById(long id) {
		String key=itemDesckey+id;
		if (jedisDaoImpl.exists(key)) {
			return jedisDaoImpl.get(key);
		}
		
		String itemDesc = tbItemDescDubboServiceImpl.seldescById(id).getItemDesc();
		jedisDaoImpl.set(key, itemDesc);
		return itemDesc;
		
		
	}

}
