package com.ego.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;

import com.ego.commons.pojo.EgoResult;
import com.ego.order.pojo.MyOrderParam;
import com.ego.pojo.TbItemChild;

public interface TbOrderService {
	/**
	 * 根据id从redis中查询出相应的物品
	 */
	List<TbItemChild> selChilds(List<Long> ids,HttpServletRequest request);
	
	/**
	 * 将controller中的内容添加到mysql中
	 */
	
	EgoResult save(MyOrderParam param,HttpServletRequest request);

}
