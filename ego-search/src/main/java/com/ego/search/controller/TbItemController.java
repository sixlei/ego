package com.ego.search.controller;


import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.pojo.TbItem;
import com.ego.search.service.TbItemService;
import com.github.pagehelper.PageHelper;

@Controller
public class TbItemController {
	@Resource
	private TbItemService tbItemServiceImpl;
	//如何解决get的中文乱码！！！很重要
	@RequestMapping(value="solr/init",produces="text/html;charset=utf-8") //加上produces可以避免乱码现象
	@ResponseBody
	public String init(){
		long start = System.currentTimeMillis();
		try {
			
			tbItemServiceImpl.init();
			long end = System.currentTimeMillis();
			return "消耗了"+(end-start)/1000+"秒";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "初始化失败";
		} 
	}
	
	
	@RequestMapping("search.html")
	public String select(String q,@RequestParam(defaultValue="1") int page,@RequestParam(defaultValue="24")int rows,Model model){
		//q是通过get得到的，所以不能通过过滤器解决中文乱码，应该这样做
		try {
			q=new String(q.getBytes("iso-8859-1") ,"utf-8");
			Map<String, Object> map = tbItemServiceImpl.selByQuery(q, page, rows);
			model.addAttribute("query", q);
			model.addAttribute("itemList", map.get("itemList"));
			model.addAttribute("totalPages", map.get("totalPages"));
			model.addAttribute("page", page);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
		return "search";
	}
	
	@RequestMapping("solr/add")
	@ResponseBody
	public int add(@RequestBody TbItem tbItem){
		System.out.println(tbItem);
		try {
			return tbItemServiceImpl.insTbItem(tbItem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return 0;
	}
}
