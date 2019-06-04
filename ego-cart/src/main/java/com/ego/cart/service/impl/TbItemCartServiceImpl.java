package com.ego.cart.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.cart.service.TbItemCartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemsService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemChild;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;

@Service
public class TbItemCartServiceImpl implements TbItemCartService{
	
	@Resource
	private JedisDao jedisDaoImpl;
	
	@Value("${cart.user.key}")
	private String cartkey;
	
	@Reference
	private TbItemsService tbItemsServiceImpl;

	@Override
	public void insRedis(long id, int num,HttpServletRequest request) {
		
		//获取登录者信息,得到username即为redis的key
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String post = HttpClientUtil.doPost("http://localhost:8084/user/token/"+token);
		EgoResult er = JsonUtils.jsonToPojo(post, EgoResult.class);
		String key=cartkey+((LinkedHashMap)er.getData()).get("username");
		
		//获得商品信息，放入到redis中
		
		TbItem tbItem = tbItemsServiceImpl.selById(id);
		List<TbItemChild> items=new ArrayList<>();
		if (jedisDaoImpl.exists(key)) {
			//获取到该用户已经加入了哪些商品,判断该商品是否在列表中，如果在的话，就将其数据加上num
			String list = jedisDaoImpl.get(key);
			if (list!=null&&!list.equals("")) {
				items = JsonUtils.jsonToList(list, TbItemChild.class);
				if (items!=null&&items.size()>0) {
					for (TbItemChild item : items) {
						if (item.getId().equals(id)) {
							item.setNum(num+item.getNum());
							jedisDaoImpl.set(key, JsonUtils.objectToJson(items));
							return ;
						}
					}
				}
			}
		}
		
		TbItemChild tbItemChild = new TbItemChild();
		tbItemChild.setId(tbItem.getId());
		tbItemChild.setImages(tbItem.getImage()!=null&&!tbItem.getImage().equals("")?tbItem.getImage().split(","):new String[1]);
		tbItemChild.setNum(num);
		tbItemChild.setPrice(tbItem.getPrice());
		tbItemChild.setTitle(tbItem.getTitle());
		items.add(tbItemChild);
		jedisDaoImpl.set(key, JsonUtils.objectToJson(items));
		return ;
		
	}

	@Override
	public List<TbItemChild> selitems(HttpServletRequest request) {
		//查询出要显示的key
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String doPost = HttpClientUtil.doPost("http://localhost:8084/user/token/"+token);
		EgoResult er = JsonUtils.jsonToPojo(doPost, EgoResult.class);
		String key=cartkey+((LinkedHashMap)er.getData()).get("username");
		
		String json = jedisDaoImpl.get(key);
		return JsonUtils.jsonToList(json, TbItemChild.class);
		
	}
	
	@Override
	public EgoResult updNums(long id, int num,HttpServletRequest request) {
		EgoResult egoResult = new EgoResult();
		//查询key
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String doPost = HttpClientUtil.doPost("http://localhost:8084/user/token/"+token);
		EgoResult er = JsonUtils.jsonToPojo(doPost, EgoResult.class);
		String key=cartkey+((LinkedHashMap)er.getData()).get("username");
		
		//从redis中取出商品列表
		String json = jedisDaoImpl.get(key);
		List<TbItemChild> tbItems = JsonUtils.jsonToList(json, TbItemChild.class);
		for (TbItemChild tbItemChild : tbItems) {
			if (tbItemChild.getId().equals(id)) {
				tbItemChild.setNum(num);
			}
		}
		String ok = jedisDaoImpl.set(key, JsonUtils.objectToJson(tbItems));
		if (ok.equals("ok")) {
			egoResult.setStatus(200);
		}
		return egoResult;
	}

	@Override
	public EgoResult delItems(long id, HttpServletRequest request) {
		EgoResult egoResult = new EgoResult();
		//查询key
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String doPost = HttpClientUtil.doPost("http://localhost:8084/user/token/"+token);
		EgoResult er = JsonUtils.jsonToPojo(doPost, EgoResult.class);
		String key=cartkey+((LinkedHashMap)er.getData()).get("username");
		
		//获取商品列表
		String json = jedisDaoImpl.get(key);
		List<TbItemChild> tbItems = JsonUtils.jsonToList(json, TbItemChild.class);
		TbItemChild tb = new TbItemChild();
		for (TbItemChild tbItemChild : tbItems) {
			if(tbItemChild.getId().equals(id)){
				tb=tbItemChild;
			}
		}
		tbItems.remove(tb);
		String ok = jedisDaoImpl.set(key, JsonUtils.objectToJson(tbItems));
		if (ok.equals("OK")) {
			egoResult.setStatus(200);
		}
		return egoResult;
	}
	
	
	
}
