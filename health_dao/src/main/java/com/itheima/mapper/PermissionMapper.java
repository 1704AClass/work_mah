package com.itheima.mapper;

import java.util.Set;

import com.itheima.pojo.Permission;

public interface PermissionMapper {
	//用角色的id来查询它的权限
	Set<Permission> findByRoleId(Integer roleId);

}
