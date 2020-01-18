/**  
* @Title: SetmealService.java  
* @Package com.itheima.service  
* @Description: TODO(用一句话描述该文件做什么)  
* @author mah  
* @date 2020年1月18日  
* @version V1.0  
*/  
package com.itheima.service;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;

/**  
* @ClassName: SetmealService  
* @Description: TODO(这里用一句话描述这个类的作用)  
* @author mah  
* @date 2020年1月18日    
*/
public interface SetmealService {

	
	void add(Setmeal setmeal, Integer[] checkgroupIds);

	
	
	List<Setmeal> findAll();

	
	PageResult pageQuery(QueryPageBean queryPageBean);
	
}
