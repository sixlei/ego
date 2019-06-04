package com.ego.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;


public class LoginInterceptor implements HandlerInterceptor{
	/**
	 * 要在拦截器的pre中判断是否有登陆信息，如果没有登陆，就跳到登陆页面中，登陆过的话就放过
	 */

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//从cookie中获取token
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		//从passport中的user/token/{token}中获取登陆信息
		if (token!=null&&!token.equals("")) {
			
			String json = HttpClientUtil.doPost("http://localhost:8084/user/token/"+token);
			EgoResult er = JsonUtils.jsonToPojo(json, EgoResult.class);
			if(er.getStatus()==200){
				return true;
			}
		}
		String num = request.getParameter("num");
		//request.getRequestURL()可以得到目标也就是拦截下来的url,这里是想让他登陆后直接跳到目标链接，所以在login中传入此链接
//		System.out.println(request.getRequestURL());
		
		if (num!=null&&!num.equals("")) {
			
			response.sendRedirect("http://localhost:8084/user/showLogin?interUrl="+request.getRequestURL()+"%3Fnum="+num);
		}else {
			response.sendRedirect("http://localhost:8084/user/showLogin?interUrl="+request.getRequestURL());
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
