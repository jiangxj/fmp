package com.luckymiao.common.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.luckymiao.common.ResponseJson;
import com.luckymiao.common.utils.StringUtils;

@Controller
@RequestMapping("attachment")
public class UploadController {
	@RequestMapping("/upload")
	@ResponseBody
	public String upload(HttpServletRequest request, HttpServletResponse response){
		
		String basePath = request.getRealPath("/");
		String filepath = "attachment" + File.separator + "image" + File.separator;
		String localIp = request.getLocalAddr();
		String port = String.valueOf(request.getServerPort());
		String httpBaseUrl = "http://"+localIp + ":"+ port+"/";
		String targetPath = basePath + filepath;
		ResponseJson jsonObj = new ResponseJson();
		String callback = request.getParameter("callback");
		String nfilename = "";
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			int size_limit = 20;
			if(multipartRequest!= null){
				MultipartFile mpf = multipartRequest.getFile("attachment");
				if(mpf != null){
					String ofilename = mpf.getOriginalFilename();
					long fileSize = mpf.getSize();
					if(fileSize/1000000>size_limit){
						jsonObj.setCode("0");
						jsonObj.setCodemsg("文件不能大于2M！");
					}else if("".equals(ofilename)){
						jsonObj.setCode("2");
						jsonObj.setCodemsg("未发现图片！");
					}else{
						String ext = ofilename.substring(ofilename.lastIndexOf("."));
						nfilename = StringUtils.getUUID()+ext;
						String targetFilePath = targetPath + nfilename;
						mpf.transferTo(new File(targetFilePath));
						String httpFileUrl = httpBaseUrl + "eklm/" + "attachment/image/" + nfilename;
						jsonObj.setCode("1");
						jsonObj.setCodemsg("成功上传！");
						Map<String, String> data = new HashMap();
						data.put("fileurl", httpFileUrl);
						jsonObj.setData(data);
					}
				}else{
					jsonObj.setCode("4");
					jsonObj.setCodemsg("未发现图片，请以attachment作为参数名称上传！");
				}
			}else{
				jsonObj.setCode("5");
				jsonObj.setCodemsg("未发现图片，请确保form的enctype=\"multipart/form-data\"！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonObj.setStatusError();
		}
		if(!org.apache.commons.lang.StringUtils.isBlank(callback)){
			jsonObj.setJsonp(true);
			jsonObj.setCallback(callback);
		}
		request.setAttribute("data", jsonObj.toString());
		return jsonObj.toString();
	}
	@RequestMapping("/empty")
	@ResponseBody
	public String empty(HttpServletRequest request, HttpServletResponse response){
		return "";
	}
	
	@RequestMapping("/image_upload_kindeditor")
	@ResponseBody
	public String upload_kindeditor(HttpServletRequest request, HttpServletResponse response){
		
		String basePath = request.getRealPath("/");
		String filepath = "images" + File.separator + "news" + File.separator;
		String localIp = request.getLocalAddr();
		String port = String.valueOf(request.getServerPort());
		String httpBaseUrl = "http://"+localIp + ":"+ port+"/";
		String targetPath = basePath + filepath;
		String nfilename = "";
		Map resultMap = new HashMap();
		JSONObject obj = new JSONObject();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			if(multipartRequest!= null){
				MultipartFile mpf = multipartRequest.getFile("imgFile");
				if(mpf != null){
					String ofilename = mpf.getOriginalFilename();
					if("".equals(ofilename)){
						resultMap.put("error", 1);
						resultMap.put("message", "未发现图片！");
					}else{
						String ext = ofilename.substring(ofilename.lastIndexOf("."));
						nfilename = StringUtils.getUUID()+ext;
						String targetFilePath = targetPath + nfilename;
						mpf.transferTo(new File(targetFilePath));
						
						String httpFileUrl = httpBaseUrl + "wxl/" + "images/news/" + nfilename;
						resultMap.put("error", 0);
						resultMap.put("url", httpFileUrl);
						
						obj.put("error", 0);
						obj.put("url", httpFileUrl);
					}
				}else{
					resultMap.put("error", 1);
					resultMap.put("message", "未发现图片，请以dir作为参数名称上传！");
				}
			}else{
				resultMap.put("error", 1);
				resultMap.put("message", "未发现图片，请确保form的enctype=\"multipart/form-data\"！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return obj.toString();
	}
}
