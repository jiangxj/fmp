package com.luckymiao.cooperation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luckymiao.cooperation.service.T05_cooperation_jykService;

@Controller
@RequestMapping("jyk")
public class T05_cooperation_jykController {
	private T05_cooperation_jykService t05_cooperation_jykService;
	private String check(HttpServletRequest request, HttpServletResponse response){
		
	}
}
