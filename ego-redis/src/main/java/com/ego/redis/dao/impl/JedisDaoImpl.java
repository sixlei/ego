package com.ego.redis.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import redis.clients.jedis.JedisCluster;

import com.ego.redis.dao.JedisDao;
@Repository
public class JedisDaoImpl implements JedisDao{
	@Resource
	private JedisCluster jedisCluster;

	@Override
	public Boolean exists(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.exists(key);
	}

	@Override
	public Long del(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.del(key);
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.get(key);
	}

	@Override
	public String set(String key, String value) {
		// TODO Auto-generated method stub
		return jedisCluster.set(key, value);
	}

	@Override
	public Long expire(String key, int seconds) {
		return jedisCluster.expire(key, seconds);
	}
	

}
