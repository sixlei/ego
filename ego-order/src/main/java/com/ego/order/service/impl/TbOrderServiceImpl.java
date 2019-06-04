package com.ego.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemsService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.TbOrderService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemChild;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import com.ego.redis.dao.JedisDao;
@Service
public class TbOrderServiceImpl implements TbOrderService{
	
	@Resource
	private JedisDao jedisDaoImpl;
	
	@Reference
	private TbOrderDubboService tbOrderDubboServiceImpl;
	
	@Value("${cart.user.key}")
	private String cartkey;
	
	@Reference
	private TbItemsService tbItemsServiceImpl;
	
	@Override
	public List<TbItemChild> selChilds(List<Long> ids,HttpServletRequest request) {
		//从cookie中取出token,通过请求得到用户信息进而得到key
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String post = HttpClientUtil.doPost("http://localhost:8084/user/token/"+token);
		EgoResult er = JsonUtils.jsonToPojo(post, EgoResult.class);
		String key=cartkey+((LinkedHashMap)er.getData()).get("username");
		
		//从redis中取出key对应的物品信息
		List<TbItemChild> list = JsonUtils.jsonToList(jedisDaoImpl.get(key), TbItemChild.class);
		List<TbItemChild> items=new ArrayList<>();
		for (TbItemChild tbItemChild : list) {
			for (long id:ids) {
				if ((long)tbItemChild.getId()==(long)id) {
					/**
					 * 判断一下库存余量是否还足够
					 */
					if (tbItemsServiceImpl.selById(id).getNum()>=tbItemChild.getNum()) {
						tbItemChild.setEnough(true);
					}else {
						tbItemChild.setEnough(false);
					}
					items.add(tbItemChild);
				}
			}
		}
		return items;
	}

	@Override
	public EgoResult save(MyOrderParam param, HttpServletRequest request) {
		EgoResult egoResult = new EgoResult();
		
		//创建tbOrder
		TbOrder tbOrder = new TbOrder();
		long orderId = IDUtils.genItemId();
		tbOrder.setOrderId(orderId+"");
		tbOrder.setPaymentType(param.getPaymentType());
		tbOrder.setPayment(param.getPayment());
		//获取买家信息
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String post = HttpClientUtil.doPost("http://localhost:8084/user/token/"+token);
		EgoResult er = JsonUtils.jsonToPojo(post, EgoResult.class);
		String key=cartkey+((LinkedHashMap)er.getData()).get("username");
		Map user=(LinkedHashMap)er.getData();
		tbOrder.setUserId(Long.parseLong(user.get("id").toString()));
		tbOrder.setBuyerNick(user.get("username")+"");
		tbOrder.setBuyerRate(0);
		Date date = new Date();
		tbOrder.setCreateTime(date);
		tbOrder.setUpdateTime(date);
		
		//完成List<TbOrderItems>
		List<TbOrderItem> tbOrderItems = param.getOrderItems();
		for (TbOrderItem tbOrderItem : tbOrderItems) {
			long orderitemid = IDUtils.genItemId();
			tbOrderItem.setId(orderitemid+"");
			tbOrderItem.setOrderId(orderId+"");
		}
		
		TbOrderShipping orderShipping = param.getOrderShipping();
		orderShipping.setOrderId(orderId+"");
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		
		try {
			int index = tbOrderDubboServiceImpl.insTbOrder(tbOrder, tbOrderItems, orderShipping);
			if (index>0) {
				/**
				 * 如果订单提交处理正常，就把redis中这些商品删除
				 */
				String json = jedisDaoImpl.get(key);
				List<TbItemChild> tbitems = JsonUtils.jsonToList(json, TbItemChild.class);
				List<TbItemChild> lists=new ArrayList<>();
				//已经购买的orderItems
				List<TbOrderItem> orderItems = param.getOrderItems();
				for (TbItemChild item:tbitems) {
					for (TbOrderItem tbOrderItem : orderItems) {
						if ((long)item.getId()==(long)Long.parseLong(tbOrderItem.getItemId())) {
							lists.add(item);
						}
					}
				}
				for (TbItemChild item:lists) {
					tbitems.remove(item);
				}
				jedisDaoImpl.set(key, JsonUtils.objectToJson(tbitems));
				 
				egoResult.setStatus(200);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return egoResult;
	}

}
