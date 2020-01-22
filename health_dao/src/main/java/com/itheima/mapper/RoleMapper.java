package com.itheima.mapper;

import java.util.Set;

import com.itheima.pojo.Role;

public interface RoleMapper {
	//用户名的id来查询是什么角色
	Set<Role> findByUserId(Integer id);

}
