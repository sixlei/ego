package com.ego.manage.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ego.manage.service.PicService;

@Controller
public class PicController {
	@Resource
	private PicService picServiceImpl;
	
	
	@RequestMapping("pic/upload")
	@ResponseBody
	public Map<String, Object> upload(MultipartFile uploadFile) throws IOException{
		Map<String, Object> upload = picServiceImpl.upload(uploadFile);
		return upload;
	}
}
