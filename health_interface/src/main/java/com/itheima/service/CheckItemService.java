package com.itheima.service;

import java.util.List;

import com.itheima.pojo.CheckItem;

import entity.PageResult;
import entity.QueryPageBean;

public interface CheckItemService {

	void add(CheckItem checkItem);

	void delete(Integer id);

	void edit(CheckItem checkItem);

	CheckItem findById(Integer id);

	List<CheckItem> findAll();

	PageResult pageQuery(QueryPageBean queryPageBean);

	void dels(Integer[] ids);

}
