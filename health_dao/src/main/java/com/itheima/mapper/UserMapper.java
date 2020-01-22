package com.itheima.mapper;

import com.itheima.pojo.User;

public interface UserMapper {
	//登录使用的
	User findOne(String username);

}
