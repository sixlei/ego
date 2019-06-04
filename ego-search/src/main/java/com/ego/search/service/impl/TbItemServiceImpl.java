package com.ego.search.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.print.Doc;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemsService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.search.pojo.TbItemChild;
import com.ego.search.service.TbItemService;

@Service
public class TbItemServiceImpl implements TbItemService{
	
	@Reference
	private TbItemsService tbItemsServiceImpl;
	@Reference
	private TbItemCatDubboService tbItemCatDubboServiceImpl;
	@Reference
	private TbItemDescDubboService tbItemDescDubboServiceImpl; 
	
	@Resource
	private CloudSolrClient cloudSolrClient;

	@Override
	public void init() throws SolrServerException, IOException {
		List<TbItem> list = tbItemsServiceImpl.selAll((byte)1);
		for (TbItem tbItem : list) {
			TbItemCat itemCat = tbItemCatDubboServiceImpl.selNameById(tbItem.getCid());
			TbItemDesc itemDesc = tbItemDescDubboServiceImpl.seldescById(tbItem.getId());
			SolrInputDocument doc = new SolrInputDocument();
			
			
			
			
			doc.setField("id", tbItem.getId());
			doc.setField("item_title", tbItem.getTitle());
			doc.setField("item_sell_point", tbItem.getSellPoint());
			doc.setField("item_price", tbItem.getPrice());
			doc.setField("item_image", tbItem.getImage());
			doc.setField("item_category_name", itemCat.getName());
			doc.setField("item_desc", itemDesc.getItemDesc());
			
			cloudSolrClient.add(doc);
			
		}
		cloudSolrClient.commit();
	}

	@Override
	public Map<String, Object> selByQuery(String query, int page, int rows) throws SolrServerException, IOException {
		Map<String, Object> selMap=new HashMap<String, Object>();
		
		//查询条件
		SolrQuery params = new SolrQuery();
		//设置q
		params.setQuery("item_keywords:"+query);
		//设置分页条件
		params.setStart(rows*(page-1));
		params.setRows(rows);
		//设置高亮
		params.setHighlight(true);
		params.addHighlightField("item_title");
		params.setHighlightSimplePre("<span style='color:red'>");
		params.setHighlightSimplePost("</span>");
		
		
		//根据查询条件查询
		QueryResponse response = cloudSolrClient.query(params);
		//高亮内容
		Map<String, Map<String, List<String>>> map = response.getHighlighting();
		//未高亮内容
		SolrDocumentList listdoc = response.getResults();
		
		//TbItemList，返回结果
		List<TbItemChild> tbItemList=new ArrayList<>();
		
		for (SolrDocument doc:listdoc) {
			TbItemChild tbItemChild = new TbItemChild();
			tbItemChild.setId(Long.parseLong(doc.getFieldValue("id").toString()));
			tbItemChild.setPrice((long)doc.getFieldValue("item_price"));
			tbItemChild.setSellPoint((String)doc.getFieldValue("item_sell_point"));
			tbItemChild.setImages(doc.getFieldValue("item_image").toString()!=null&&!doc.getFieldValue("item_image").toString().equals("")?doc.getFieldValue("item_image").toString().split(","):new String[1]);
			List<String> list = map.get(doc.getFieldValue("id")).get("item_title");
			if (list!=null&&list.size()>0) {
				tbItemChild.setTitle(list.get(0));
			}else {
				tbItemChild.setTitle((String)doc.getFieldValue("item_title"));
			}
			tbItemList.add(tbItemChild);
		}
		selMap.put("itemList", tbItemList);
		selMap.put("totalPages", listdoc.getNumFound()%rows==0?listdoc.getNumFound()/rows:listdoc.getNumFound()/rows+1);
		return selMap;
	}

	@Override
	public int insTbItem(TbItem tbItem) throws SolrServerException, IOException {
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", tbItem.getId());
		doc.setField("item_title", tbItem.getTitle());
		doc.setField("item_sell_point", tbItem.getSellPoint());
		doc.setField("item_price", tbItem.getPrice());
		doc.setField("item_image", tbItem.getImage());
		doc.setField("item_category_name", tbItemCatDubboServiceImpl.selNameById(tbItem.getCid()).getName());
		doc.setField("item_desc", tbItemDescDubboServiceImpl.seldescById(tbItem.getId()).getItemDesc());
		
		UpdateResponse response = cloudSolrClient.add(doc);
		cloudSolrClient.commit();
		if (response.getStatus()==0) {
			return 1;
		}
		
		
		return 0;
	}


}
