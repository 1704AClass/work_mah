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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.itheima.utils.MD5Utils;

public class AuthenticationProviderImpl implements AuthenticationProvider{
	
	private UserDetailsService userDetailsService;
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String name = authentication.getName();
		String password = (String) authentication.getCredentials();
		UserDetails details = userDetailsService.loadUserByUsername(name);
		if(details==null){
			throw new DisabledException("用户名有误!");
		}
		String md5 = MD5Utils.md5(password);
		if(!md5.equals(details.getPassword())){
			throw new DisabledException("密码有误!");
		}
		Collection<? extends GrantedAuthority> authorities = details.getAuthorities();
		return new UsernamePasswordAuthenticationToken(name, password,authorities);
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
