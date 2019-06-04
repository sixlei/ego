package com.ego.passport.service.impl;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;
@Service
public class TbUserServiceImpl implements TbUserService{
	@Reference
	private TbUserDubboService tbUserDubboServiceImpl;
	
	@Resource
	private JedisDao jedisDaoImpl;

	@Override
	public EgoResult selByUser(TbUser user,HttpServletRequest request,HttpServletResponse response) {
		EgoResult er = new EgoResult();
		TbUser selUser = tbUserDubboServiceImpl.selByUser(user);
		if (selUser!=null) {
			er.setStatus(200);
			/**
			 * 生成一个uuid作为redis的key，将seluser作为redis的value。生成一个cookie，将TT_TOKEN作为key，将uuid作为value。
			 */
			String key = UUID.randomUUID().toString();
			jedisDaoImpl.set(key, JsonUtils.objectToJson(selUser));
			jedisDaoImpl.expire(key, 60*60*24);
			CookieUtils.setCookie(request, response, "TT_TOKEN", key, 60*60*24);
			
		}else {
			er.setMsg("请检查用户名密码");
		}
		return er;
	}

	@Override
	public EgoResult selUserByToken(String token) {
		EgoResult er = new EgoResult();
		
		String seluser = jedisDaoImpl.get(token);
		if (seluser!=null&&!seluser.equals("")) {
			er.setStatus(200);
			er.setMsg("OK");
			TbUser user = JsonUtils.jsonToPojo(seluser, TbUser.class);
			user.setPassword("");
			er.setData(user);
			return er;
		}
		return er;
	}

	@Override
	public EgoResult delUser(String token,HttpServletRequest request,HttpServletResponse response) {
		EgoResult er = new EgoResult();
		jedisDaoImpl.del(token);
		CookieUtils.deleteCookie(request, response, "TT_TOKEN");
		er.setStatus(200);
		er.setMsg("OK");
		er.setData("");
		return er;
	}
	
	

}
