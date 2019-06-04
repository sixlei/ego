package com.ego.items.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.items.service.TbItemParamItemService;
import com.ego.pojo.TbItemParamItem;
import com.ego.redis.dao.JedisDao;
@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService{
	@Reference
	private TbItemParamItemDubboService tbItemParamItemDubboServiceImpl;
	
	@Resource
	private JedisDao jedisDaoImpl;
	
	@Value("${redis.itemparamitem.key=itemparamiyem:}")
	private String paramkey;
	
	
	@Override
	public String selById(long id) {
		String key=paramkey+id;
		//先从redis中查询，有规格参数的话直接取出，没有的话再进行数据库查询
		if (jedisDaoImpl.exists(key)) {
			if (jedisDaoImpl.get(key)!=null&&!jedisDaoImpl.get(key).equals("")) {
				return jedisDaoImpl.get(key);
			}
			
		}
		
		
		TbItemParamItem itemParamItem = tbItemParamItemDubboServiceImpl.selById(id);
		//这里将这个json解析成的实体类实现成新的类
		List<TbItemParamItemChild> list = JsonUtils.jsonToList(itemParamItem.getParamData(), TbItemParamItemChild.class);
		StringBuffer sb = new StringBuffer();
		
		for (TbItemParamItemChild param : list) {
			sb.append("<table width=500 style='color:gray;'>");
			for (int i = 0; i < param.getParams().size(); i++) {
				if (i==0) {
					sb.append("<tr>");					
					sb.append("<td align='right' width='30%'>"+param.getGroup()+"</td>");
					sb.append("<td align='right' width='30%'>"+param.getParams().get(i).getK()+"</td>");
					sb.append("<td>"+param.getParams().get(i).getV()+"</td>");
					
					sb.append("</tr>");
				}
				else {
					sb.append("<tr>");
					sb.append("<td></td>");
					sb.append("<td align='right'>"+param.getParams().get(i).getK()+"</td>");
					sb.append("<td>"+param.getParams().get(i).getV()+"</td>");
					sb.append("</tr>");
				}
			}
			sb.append("</table>");
		}
		jedisDaoImpl.set(key, sb.toString());
		return sb.toString();
	}

}
