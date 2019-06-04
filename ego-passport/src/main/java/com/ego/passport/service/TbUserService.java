package com.ego.passport.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

public interface TbUserService {
	/**
	 * 根据用户名密码查询出是否能登录，返回值是EgoResult
	 */
	EgoResult selByUser(TbUser user,HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 根据token查询用户信息
	 */
	EgoResult selUserByToken(String token);
	
	/**
	 * 退出
	 */
	EgoResult delUser(String token,HttpServletRequest request,HttpServletResponse response);
	

}
