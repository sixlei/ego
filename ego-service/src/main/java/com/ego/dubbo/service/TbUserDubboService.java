package com.ego.dubbo.service;

import com.ego.pojo.TbUser;

public interface TbUserDubboService {
	/**
	 * 根据用户用户名密码判断能否登录
	 */
	TbUser selByUser(TbUser user);

}
