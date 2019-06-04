package com.ego.manage.service;

import java.util.List;

import com.ego.commons.pojo.EasyUITree;

public interface TbItemCatService {
	List<EasyUITree> selByPid(long pid);
}
