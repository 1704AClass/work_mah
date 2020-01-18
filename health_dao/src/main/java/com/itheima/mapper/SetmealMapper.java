/**  
* @Title: SetmealMapper.java  
* @Package com.itheima.mapper  
* @Description: TODO(用一句话描述该文件做什么)  
* @author mah  
* @date 2020年1月18日  
* @version V1.0  
*/  
package com.itheima.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;

/**  
* @ClassName: SetmealMapper  
* @Description: TODO(这里用一句话描述这个类的作用)  
* @author mah  
* @date 2020年1月18日    
*/
public interface SetmealMapper {

	
	void add(Setmeal setmeal);

	
	void setSetmealAndCheckGroup(Map<String, Integer> map);


	/**
     * 检查项列表
     */
	@Select("select * from t_setmeal")
	List<Setmeal> findAll();


	/**  
	* @Title: pageQuery  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param @param queryString
	* @param @return 参数  
	* @return Page<CheckItem> 返回类型  
	* 创建人：马嫒红 job_mah@163.com    
	* 创建时间：2020年1月18日 上午11:51:55    
	* 修改人：马嫒红 job_mah@163.com       
	* 修改时间：2020年1月18日 上午11:51:55    
	* 修改备注：       
	* @version SetmealMapper@param queryString
	* @version SetmealMapper@return&lt;/pre&gt;  
	* @throws  
	*/  
	Page<Setmeal> pageQuery(String queryString);
	
	
	
}
