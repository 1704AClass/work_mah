package com.itheima.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.mapper.SetmealMapper;
import com.itheima.oss.RedisConstant;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;



@Service
public class SetmealServiceImpl implements SetmealService{
	@Autowired
	private SetmealMapper setmealMapper;
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public List<Setmeal> findAll() {
		// TODO Auto-generated method stub
		return setmealMapper.findAll();
	}

	@Override
	public Setmeal findById(Integer id) {
		// TODO Auto-generated method stub
		return setmealMapper.findById(id);
	}

	@Override
	public List<Integer> findSetmealIdAndCheckGroupId(Integer id) {
		// TODO Auto-generated method stub
		return setmealMapper.findSetmealIdAndCheckGroupId(id);
	}

	@Override
	public void edit(Setmeal setmeal, Integer[] checkGroupId) {
		// TODO Auto-generated method stub
		setmealMapper.edit(setmeal);
		setmealMapper.delsCheckGroupIdAndSetmealId(setmeal.getId());
		Integer setMealId = setmeal.getId();
		//用比较
		this.updateCheckGroupIdAndSetmealId(setMealId, checkGroupId);
	}

	@Override
	public void dels(Integer id) {
		// TODO Auto-generated method stub
		setmealMapper.delsCheckGroupIdAndSetmealId(id);
		setmealMapper.dels(id);
	}

	@Override
	public PageResult pageQuery(QueryPageBean queryPageBean) {
		// TODO Auto-generated method stub
		//获取当前页
		Integer currentPage = queryPageBean.getCurrentPage();
		//获取模糊参数
		String queryString = queryPageBean.getQueryString();
		//获取同条数
		Integer pageSize = queryPageBean.getPageSize();
		//拼接字符串
		PageHelper.startPage(currentPage, pageSize);
		//查Page对象的集合
		Page<CheckGroup> page = setmealMapper.pageQuery(queryString);
		//获取总条数
		long total = page.getTotal();
		//获取查出的集合
		List<CheckGroup> rows = page.getResult();
		//利用封装的对象返回一个集合集
		return new PageResult(total, rows);
	}

	@Override
	public void add(Setmeal setmeal, Integer[] checkGroupId) {
		// TODO Auto-generated method stub
		//建立新的套餐
		setmealMapper.add(setmeal);
		Integer setMealId = setmeal.getId();
		this.updateCheckGroupIdAndSetmealId(setMealId, checkGroupId);
		this.savePic2Redis(setmeal.getImg());
	}
	//共同方法抽取
	public void updateCheckGroupIdAndSetmealId(Integer setMealId,Integer[] checkGroupId){
		if(setMealId!=null && checkGroupId!=null && checkGroupId.length>0){
			//循环前台传过来的数组
			for (Integer checkgroupId : checkGroupId) {
				Map map = new HashMap<>();
				map.put("checkgroupId", checkgroupId);
				map.put("setMealId", setMealId);
				//去后台对中间表进行操作
				setmealMapper.updateCheckGroupIdAndSetmealId(map);
			}
		}
	}
	//将图片名称保存到Redis
	public void savePic2Redis(String img){
		redisTemplate.boundSetOps(RedisConstant.SETMEAL_PIC_DB_RESOURCES).add(img);
		/*redisTemplate.boundHashOps("imgDbAll").put(RedisConstant.SETMEAL_PIC_DB_RESOURCES, img);*/
	}

	@Override
	public Setmeal findByid(int id) {
		// TODO Auto-generated method stub
		return setmealMapper.findByid(id);
	}

	@Override
	public List<Map<String, Object>> findSetmealCount() {
		// TODO Auto-generated method stub
		return setmealMapper.findSetmealCount();
	}
}
