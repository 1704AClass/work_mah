package com.itheima.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.PermissionMapper;
import com.itheima.mapper.RoleMapper;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private PermissionMapper permissionMapper;
	@Override
	public User findOne(String username) {
		// TODO Auto-generated method stub
		User user = userMapper.findOne(username);
		//判断是否有该用户
		if(user == null){
			return null;
		}
		//获取用户id
		Integer id = user.getId();
		//根据用户id来查询角色集合
		Set<Role> roles = roleMapper.findByUserId(id);
		//判断是否为空
		if(roles != null && roles.size() > 0){
			for (Role role : roles) {
				//获取角色id
				Integer roleId = role.getId();
				//根据角色id来获取权限
				Set<Permission> permissions = permissionMapper.findByRoleId(roleId);
				if(permissions !=null && permissions.size() > 0){
					//权限赋值
					role.setPermissions(permissions);
				}
			}
			user.setRoles(roles);
		}
		return user;
	}
}
