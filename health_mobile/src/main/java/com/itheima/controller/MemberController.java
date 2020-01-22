package com.itheima.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;




/**
* 登录
*/
@RestController
@RequestMapping("/member")
public class MemberController {
	@Reference
	private MemberService memberService;
	@Autowired
	private RedisTemplate redisTemplate;
	
	@RequestMapping("/login")
	public Result login(HttpServletResponse response,@RequestBody Map map) {
		String telephone = (String) map.get("telephone");
		String validateCode = (String) map.get("validateCode");
		String codeInRedis = (String) redisTemplate.boundHashOps("SmsTelephone").get(RedisMessageConstant.SENDTYPE_ORDER);
		if(codeInRedis == null || !codeInRedis.equals(validateCode)){
			//验证码输入错误
			return new Result(false,MessageConstant.VALIDATECODE_ERROR);
		}else{
			//验证码输入正确
			//判断当前用户是否为会员
			Member member = memberService.findByTelephone(telephone);
			if(member == null){
				//当前用户不是会员，自动完成注册
				member = new Member();
				member.setPhoneNumber(telephone);
				member.setRegTime(new Date());
				memberService.add(member);
			}
			//登录成功 写入Cookie，跟踪用户
			Cookie cookie = new Cookie("login_member_telephone",telephone);
			cookie.setPath("/");//路径
			cookie.setMaxAge(60*60*24*30);//有效期30天
			response.addCookie(cookie);
			//保存会员信息到Redis中
			String json = JSON.toJSON(member).toString();
			redisTemplate.boundHashOps("member").put("会员信息", json);
			return new Result(true,MessageConstant.LOGIN_SUCCESS);
		}
	}
}
