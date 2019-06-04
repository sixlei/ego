package com.ego.items.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemsService;
import com.ego.items.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemChild;
import com.ego.redis.dao.JedisDao;
@Service
public class TbItemServiceImpl implements TbItemService{
	
	@Reference
	private TbItemsService tbItemsServiceImpl;
	
	@Resource
	private JedisDao jedisDaoImpl;
	
	
	@Value("${redis.item.key}")
	private String itemkey;

	@Override
	public TbItemChild selByid(long id) {
		String key=itemkey+id;
		if (jedisDaoImpl.exists(key)) {
			String item = jedisDaoImpl.get(key);
			if (item!=null&&!item.equals("")) {
				return JsonUtils.jsonToPojo(item, TbItemChild.class);
			}
		}
		TbItemChild item = new TbItemChild();
		TbItem tbItem = tbItemsServiceImpl.selById(id);
		item.setId(id);
		item.setTitle(tbItem.getTitle());
		item.setImages(tbItem.getImage()!=null&&!tbItem.getImage().equals("")?tbItem.getImage().split(","):new String[1]);
//		item.setImages(tbItem.getImage().split(","));
		item.setPrice(tbItem.getPrice());
		item.setSellPoint(tbItem.getSellPoint());
		
		//将数据放入到redis中
		jedisDaoImpl.set(key, JsonUtils.objectToJson(item));
		
		return item;
	}

}
