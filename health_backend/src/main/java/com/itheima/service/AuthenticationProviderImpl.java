package com.itheima.service;

import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.itheima.utils.MD5Utils;
/**
 *	自定义安全框架
 * */
public class AuthenticationProviderImpl implements AuthenticationProvider{
	
	private UserDetailsService userDetailsService;
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		//获取到用户名
		String name = authentication.getName();
		//获取到密码
		String password = (String) authentication.getCredentials();
		//验证数据库和权限
		UserDetails details = userDetailsService.loadUserByUsername(name);
		//判断用户名
		if(details==null){
			throw new DisabledException("用户名有误!");
		}
		//判断密码MD5密码
		String md5 = MD5Utils.md5(password);
		if(!md5.equals(details.getPassword())){
			throw new DisabledException("密码有误!");
		}
		//返回成功的权限
		Collection<? extends GrantedAuthority> authorities = details.getAuthorities();
		return new UsernamePasswordAuthenticationToken(name, password,authorities);
	}

	//开启自定义
	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
