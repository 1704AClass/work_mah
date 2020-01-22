package com.itheima.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;


@RestController
@RequestMapping("/user")
public class UserController {

	@RequestMapping("/getUsername")
	public Result getUsername() {
		//安全框架直接获取用户名
		try {
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			return new Result(true,MessageConstant.GET_USERNAME_SUCCESS,name);
		} catch (Exception e) {
			return new Result(false, MessageConstant.GET_USERNAME_FAIL);
		}
	}
}
