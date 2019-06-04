package com.ego.order.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ego.commons.pojo.EgoResult;
import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.TbOrderService;
import com.ego.pojo.TbItemChild;

@Controller
public class TbOrderController {
	@Resource
	private TbOrderService tbOrderServiceImpl;

	@RequestMapping("order/order-cart.html")
	public String showOrder(@RequestParam("id") List<Long> ids,HttpServletRequest request,Model model){
		List<TbItemChild> childs = tbOrderServiceImpl.selChilds(ids, request);
		model.addAttribute("cartList", childs);
		return "order-cart";
	}
	
	@RequestMapping(value="order/create.html",produces="text/html;charset=utf-8")
	public String save(MyOrderParam param,HttpServletRequest request){
		EgoResult er = tbOrderServiceImpl.save(param, request);
		if (er.getStatus()==200) {
			return "my-orders";
		}else {
			return "exception";
		}
	}
}
