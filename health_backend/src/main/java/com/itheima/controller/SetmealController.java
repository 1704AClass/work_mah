package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 检查项管理
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference//远程调用服务
    private SetmealService setmealService;

    
    
    /**
     * 分页查询
     */ 
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
    	System.out.println("进入分页");
        PageResult pageResult = setmealService.pageQuery(queryPageBean);
        return pageResult;
    }

    //查询所有
    @RequestMapping("/findAll")
    public Result findAll() {
        List<Setmeal> setmealList = setmealService.findAll();
     	System.out.println("查询所有");
        if (setmealList != null && setmealList.size() > 0) {
            Result result = new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS);
            result.setData(setmealList);
            return result;
        }
        return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
    }
    
    /**
     * 新增
     */ 
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds) {
        try {
        	setmealService.add(setmeal,checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            //新增套餐失败 
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        //新增套餐成功
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }
}
