package com.itheima.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;

public interface SetmealMapper {
	@Select("select * from t_setmeal")
	List<Setmeal> findAll();
	//回显使用
	Setmeal findById(Integer id);
	//查询中间表
	List<Integer> findSetmealIdAndCheckGroupId(Integer id);
	//重新增加中间表
	void updateCheckGroupIdAndSetmealId(Map map);
	//编辑
	void edit(Setmeal setmeal);
	//新增
	void add(Setmeal setmeal);
	//修改时删除中间表
	void delsCheckGroupIdAndSetmealId(Integer id);
	//单删除
	void dels(Integer id);
	//分页查询
	Page<CheckGroup> pageQuery(String queryString);
	//用mybatis查询多对多
	Setmeal findByid(int id);
	//查询项目数量集合
	List<Map<String, Object>> findSetmealCount();

}
