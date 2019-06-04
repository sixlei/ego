package com.ego.search.service;

import java.io.IOException;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

import com.ego.pojo.TbItem;

public interface TbItemService {
	/**
	 * 能够查出商品的所有信息，商品的cat的所有信息，商品的描述的所有信息
	 */
	void init() throws SolrServerException, IOException;
	
	/**
	 * 根据查询条件查询出物品信息
	 */
	Map<String, Object> selByQuery(String query,int page,int rows) throws SolrServerException, IOException;
	
	/**
	 * 添加一件商品
	 */
	int insTbItem(TbItem tbItem) throws SolrServerException, IOException;

}
