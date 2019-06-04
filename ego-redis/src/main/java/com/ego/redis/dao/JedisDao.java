package com.ego.redis.dao;

import java.nio.channels.SelectableChannel;

public interface JedisDao {
	//是否存在
	Boolean exists(String key);
	//删除
	Long del(String key);
	//存放
	String get(String key);
	//取值
	String set(String key,String value);
	
	/**
	 * 设置存放时间
	 */
	Long expire(String key,int seconds);
	
}
