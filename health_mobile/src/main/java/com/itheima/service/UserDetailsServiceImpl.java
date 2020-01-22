package com.itheima.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.itheima.pojo.Member;


public class UserDetailsServiceImpl implements UserDetailsService{
	
	private MemberService memberService;
	
	
	public MemberService getMemberService() {
		return memberService;
	}


	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		List<GrantedAuthority> grantedAuths=new ArrayList<GrantedAuthority>();
		grantedAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));
		Member member = memberService.findOne(username);
		System.out.println("有");
		if(member!=null){
        	return new User(username,member.getPassword(), grantedAuths);
		}
		return null;
	}
}
