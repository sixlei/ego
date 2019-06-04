package com.ego.manage.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ego.commons.utils.FtpUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.manage.service.PicService;
@Service
public class PicServiceImpl implements PicService{
	
	@Value("${ftp.host}")
	private String host;
	@Value("${ftp.port}")
	private int port;
	@Value("${ftp.username}")
	private String username;
	@Value("${ftp.password}")
	private String password;
	@Value("${ftp.basePath}")
	private String basePath;
	@Value("${ftp.filepath}")
	private String filePath;
	
	@Override
	public Map<String, Object> upload(MultipartFile file) throws IOException {
		Map<String,Object> res=new HashMap<>();
		FtpUtil ftpUtil = new FtpUtil();
		String filename=IDUtils.genImageName()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		boolean result = ftpUtil.uploadFile(host, port, username, password, basePath, filePath, filename, file.getInputStream());
		if (result) {
			res.put("error", 0);
			res.put("url", "http://"+host+"/"+filename);
		}else {
			res.put("error", 1);
			res.put("message", "图片上传失败");
		}
		return res;
	}

}
