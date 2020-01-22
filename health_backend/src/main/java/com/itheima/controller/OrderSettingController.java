package com.itheima.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.oss.POIUtils;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;



/**
* 预约服务
*/
@RequestMapping("/ordersetting")
@RestController
public class OrderSettingController {
	
	@Reference
	private OrderSettingService orderSettingService;
	
	/**
	* Excel文件上传
	*/
	@RequestMapping("/upload")
	public Result upload(@RequestParam("excelFile") MultipartFile excelFile) {
		try {
			//读取Excel
			List<String []> list = POIUtils.readExcel(excelFile);
			if(list !=null && list.size() > 0){
				List<OrderSetting> orderSettingList = new ArrayList<>();
				for (String[] strings : list) {
					OrderSetting orderSetting = new OrderSetting(new Date(strings[0]),Integer.parseInt(strings[1]));
					orderSettingList.add(orderSetting);
				}
				orderSettingService.add(orderSettingList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
		}
		return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
	}
	/**
	* 根据日期查询预约设置数据(获取指定日期所在月份的预约设置数据)
	*/
	@RequestMapping("/getOrderSettingByMonth")
	public Result getOrderSettingByMonth(String date){
		try{
			List<Map> list = orderSettingService.getOrderSettingByMonth(date);
			//获取预约设置数据成功
			return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
		}catch (Exception e){
			e.printStackTrace();
			//获取预约设置数据失败
			return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
		}
	}
	/**
	* 根据指定日期修改可预约人数
	*/
	@RequestMapping("/editNumberByDate")
	public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
		try{
			orderSettingService.editNumberByDate(orderSetting);
			//预约设置成功
			return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
		}catch (Exception e){
			e.printStackTrace();
			//预约设置失败
			return new Result(false,MessageConstant.ORDERSETTING_FAIL);
		}
	}
}
