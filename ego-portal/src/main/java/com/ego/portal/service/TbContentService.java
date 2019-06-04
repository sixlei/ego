package com.ego.portal.service;

public interface TbContentService {
	/**
	 * 查询出指定数量的tbcontent,最后传给前端的是json数据，所以返回类型是string
	 */ 
	String selByCount(); 

}
