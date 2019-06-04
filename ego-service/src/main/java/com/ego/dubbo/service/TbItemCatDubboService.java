package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbItemCat;

public interface TbItemCatDubboService {
	List<TbItemCat> show(long pid); 
	/**
	 * 根据id得到cat的name
	 */
	TbItemCat selNameById(long id);

}
