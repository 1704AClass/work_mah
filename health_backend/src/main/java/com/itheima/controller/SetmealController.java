package com.itheima.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.oss.AliyunOSSClientUtils;
import com.itheima.oss.RedisConstant;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;



/**
 * 套餐
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
	@Reference
	private SetmealService	setmealService;
	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
     * 套餐新增
     */ 
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkGroupId) {
        try {
        	setmealService.add(setmeal,checkGroupId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }
    
    /**
     * 上传图片
     */ 
    @RequestMapping("/upload")
	public Result upload(MultipartFile imgFile) throws Exception{
		String imageUrl;
		try {
			AliyunOSSClientUtils aliyunOSSClientUtils = new AliyunOSSClientUtils();
			String uploadImg2Oss = aliyunOSSClientUtils.uploadImg2Oss(imgFile);
			imageUrl = aliyunOSSClientUtils.getImgUrl(uploadImg2Oss);
			/*redisTemplate.boundHashOps("imgAll").put(RedisConstant.SETMEAL_PIC_RESOURCES, imageUrl);*/
			redisTemplate.boundSetOps(RedisConstant.SETMEAL_PIC_RESOURCES).add(imageUrl);
			return new Result(true, MessageConstant.UPLOAD_SUCCESS,imageUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
	}
    /**
     * 套餐分页查询
     */ 
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = setmealService.pageQuery(queryPageBean);
        return pageResult;
    }

    /**
     * 套餐删除
     */ 
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
        	setmealService.dels(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /**
     * 套餐批量编辑
     */
    @RequestMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal,Integer[] checkGroupId) {
        try {
        	setmealService.edit(setmeal,checkGroupId);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    /**
     * 套餐中间表查询
     */
    @RequestMapping("/findSetmealIdAndCheckGroupId")
    public Result findSetmealIdAndCheckGroupId(Integer id) {
    	try {
        	List<Integer> checkGroupId = setmealService.findSetmealIdAndCheckGroupId(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkGroupId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
	}
    
    /**
     * 套餐批量编辑回显
     */
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
        	Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
    /**
     * 套餐列表
     */
    @RequestMapping("/findAll")
    public Result findAll() {
    	List<Setmeal> setMealList = setmealService.findAll();
        if (setMealList != null && setMealList.size() > 0) {
            Result result = new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS);
            result.setData(setMealList);
            return result;
        }
        return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
    }
}
