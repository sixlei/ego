package com.ego.manage.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemsService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
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
	public EasyUIDataGrid show(int page, int rows) {
		EasyUIDataGrid pages = tbItemsServiceImpl.selByPages(page, rows);
		
		return pages;
	}

	@Override
	public int updStus(String ids, byte status) {
		int index=0;
		String[] strings = ids.split(",");
		for (String id : strings) {
			TbItem tbItem = new TbItem();
			tbItem.setId(Long.parseLong(id));
			tbItem.setStatus(status);
			tbItemsServiceImpl.updStus(tbItem);
			index++;
			if (status==2||status==3) {
				jedisDaoImpl.del(itemkey+id);
			}
		}
		if (index==strings.length) {
			return 1;
		}
		return 0;
	}

	@Override
	public int save(TbItem tbItem, String desc,String itemparamString) throws Exception {
		final TbItem finalItem=tbItem;
		long genItemId = IDUtils.genItemId();
		tbItem.setId(genItemId);
		Date date = new Date();
		tbItem.setCreated(date);
		tbItem.setUpdated(date);
		tbItem.setStatus((byte)1);
		
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(tbItem.getId());
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(date);
		tbItemDesc.setUpdated(date);
		
		TbItemParamItem paramItem = new TbItemParamItem();
		paramItem.setItemId(genItemId);
		paramItem.setCreated(date);
		paramItem.setUpdated(date);
		paramItem.setParamData(itemparamString);

		int result = tbItemsServiceImpl.insTbItemDesc(tbItem, tbItemDesc,paramItem);
		System.out.println("result:"+result);
		System.out.println(tbItem.getId());
		new Thread(){
			public void run() {
				HttpClientUtil.doPostJson("http://localhost:8083/solr/add", JsonUtils.objectToJson(finalItem));
			};
		}.start();
		
		
		
		return result;
	}

}
