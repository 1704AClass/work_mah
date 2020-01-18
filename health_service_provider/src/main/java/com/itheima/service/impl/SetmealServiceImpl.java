/**  
* @Title: SetmealServiceImpl.java  
* @Package com.itheima.service.impl  
* @Description: TODO(用一句话描述该文件做什么)  
* @author mah  
* @date 2020年1月18日  
* @version V1.0  
*/  
package com.itheima.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.mapper.SetmealMapper;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;

/**  
* @ClassName: SetmealServiceImpl  
* @Description: TODO(这里用一句话描述这个类的作用)  
* @author mah  
* @date 2020年1月18日    
*/
@Service
public class SetmealServiceImpl implements SetmealService{

	@Autowired
	private SetmealMapper setmealMapper;
	//新增套餐
	@Override
	public void add(Setmeal setmeal, Integer[] checkgroupIds) {
		// TODO Auto-generated method stub
		setmealMapper.add(setmeal);
		if (checkgroupIds != null && checkgroupIds.length >0) {
			//绑定套餐和检查组的多对多关系
			setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
		}
	}
	//绑定套餐和检查组的多对多关系
	private void setSetmealAndCheckGroup(Integer id, Integer[]checkgroupIds) {
		for (Integer checkgroupId : checkgroupIds) {
			Map<String, Integer> map = new HashMap<>();
			map.put("setmeal_id",id);
			map.put("checkgroup_id",checkgroupId);
			setmealMapper.setSetmealAndCheckGroup(map);
		}
	}

	
	@Override
	public List<Setmeal> findAll() {
		// TODO Auto-generated method stub
		return setmealMapper.findAll();
	}

	@Override
	public PageResult pageQuery(QueryPageBean queryPageBean) {
		// TODO Auto-generated method stub
		//当前页
		Integer currentPage = queryPageBean.getCurrentPage();
		//总条数
		Integer pageSize = queryPageBean.getPageSize();
		//模糊
		String queryString = queryPageBean.getQueryString();
		//拼接字符串
		PageHelper.startPage(currentPage,pageSize);
		//page对象的数据
		Page<Setmeal> page = setmealMapper.pageQuery(queryString);
		long total = page.getTotal();
		List<Setmeal> rows = page.getResult();
		return new PageResult(total, rows);	
	}

	

}
