package com.ego.passport.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EgoResult;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;

@Controller
public class TbUserController {
	@Resource 
	private TbUserService tbUserServiceImpl;
	
	/**
	 * 这里想实现从哪跳过来再跳回到哪里，是在login.jsp中实现的，jsp中想要得到的是redirect，只需要通过作用域进行传值，将原先的url传过去，
	 * 跳转过来之前的url可以通过headr里面的Referer得到
	 * @return
	 */
	@RequestMapping("user/showLogin")
	public String loginPage(@RequestHeader(value="Referer",defaultValue="") String url,Model model,String interUrl){
		if (interUrl!=null&&!interUrl.equals("")) {
			model.addAttribute("redirect", interUrl);
		}
		model.addAttribute("redirect", url);
		return "login";
	}
	
	@RequestMapping("user/login")
	@ResponseBody
	public EgoResult login(TbUser user,HttpServletRequest request,HttpServletResponse response){
		EgoResult result = tbUserServiceImpl.selByUser(user,request,response);
		return result;
	}
	
	@RequestMapping("user/token/{token}")
	@ResponseBody
	public Object showlogin(@PathVariable String token,String callback){
		EgoResult er = tbUserServiceImpl.selUserByToken(token);
		
		if (callback!=null&&!callback.equals("")) {
			MappingJacksonValue mvj = new MappingJacksonValue(er);
			mvj.setJsonpFunction(callback);
			return mvj;
		}
		return er;
	}
	
	
	@RequestMapping("user/logout/{token}")
	public  Object  logout(@PathVariable String token,String callback,HttpServletRequest request,HttpServletResponse response) {
		EgoResult er = tbUserServiceImpl.delUser(token, request, response);
		
		if (callback!=null&&!callback.equals("")) {
			MappingJacksonValue mjv = new MappingJacksonValue(er);
			mjv.setJsonpFunction(callback);
			return mjv;
			
		}
		return er;
		
	}
}
