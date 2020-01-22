package com.itheima.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;

import entity.Result;

/**
 * 获取模型数据
 * */
@RequestMapping("/setmeal")
@RestController
public class SetmealController {
	@Reference
	private SetmealService setmealService;
	//获取模型数据
	@RequestMapping("/getAllSetmeal")
	public Result getAllSetmeal() {
		try {
			List<Setmeal> list = setmealService.findAll();
			return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,MessageConstant.GET_SETMEAL_LIST_FAIL);
		}
	}
	@RequestMapping("/findByid")
	private Result findByid(int id) {
		// TODO Auto-generated method stub
		try {
			Setmeal setmeal = setmealService.findByid(id);
			return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
		}
	}
}
