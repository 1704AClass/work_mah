package com.itheima.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;

/**
 *用户验证数据库
 */
public class UserDetailsServiceImpl implements UserDetailsService{
	//调用接口
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	//内置方法来获取前台输入的用户名
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		com.itheima.pojo.User user = userService.findOne(username);
		if(user == null){
			return null;
		}
		//用list集合来动态来拼接权限
		List<GrantedAuthority> list = new ArrayList<>();
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			list.add(new SimpleGrantedAuthority(role.getKeyword()));
			Set<Permission> permissions = role.getPermissions();
			for (Permission permission : permissions) {
				list.add(new SimpleGrantedAuthority(permission.getKeyword()));
			}
		}
        return new User(username,user.getPassword(), list);
	}
}
