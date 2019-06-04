package com.ego.cart.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.Log;
import com.ego.cart.service.TbItemCartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemChild;

@Controller
public class TbCartController {
	@Resource
	private TbItemCartService tbItemCartServiceImpl;
	
	
	@RequestMapping("cart/add/{id}.html")
	public String cartPage(@PathVariable long id,int num,HttpServletRequest request){
		tbItemCartServiceImpl.insRedis(id, num, request);
		return "cartSuccess";
	}
	
	@RequestMapping("cart/cart.html")
	public String cartadd(HttpServletRequest request,Model model){
		
		List<TbItemChild> selitems = tbItemCartServiceImpl.selitems(request);
		model.addAttribute("cartList", selitems);
		return "cart";
	}
	
	@RequestMapping("cart/update/num/{id}/{num}.action")
	@ResponseBody
	public EgoResult cartNums(@PathVariable long id,@PathVariable int num,HttpServletRequest request){
		EgoResult er = tbItemCartServiceImpl.updNums(id, num, request);
		return er;
	}
	
	@RequestMapping("cart/delete/{id}.action")
	@ResponseBody
	public EgoResult delCart(@PathVariable long id,HttpServletRequest request){
		EgoResult er = tbItemCartServiceImpl.delItems(id, request);
		return er;
	}

}
