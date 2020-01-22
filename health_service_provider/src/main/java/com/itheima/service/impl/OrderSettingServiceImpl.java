package com.itheima.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.OrderSettingMapper;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
/**
* 预约服务
*/
@Service
public class OrderSettingServiceImpl implements OrderSettingService{
	
	@Autowired
	private OrderSettingMapper orderSettingMapper;
	@Override
	public void add(List<OrderSetting> orderSettingList) {
		// TODO Auto-generated method stub
		if(orderSettingList !=null && orderSettingList.size() > 0){
			for (OrderSetting orderSetting : orderSettingList) {
				long count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
				if(count > 0){
					//如果有的话，更新一下表
					orderSettingMapper.editNumberByOrderDate(orderSetting);
				}else{
					//不存在，执行添加操作
					orderSettingMapper.add(orderSetting);
				}
			}
		}
	}
	@Override
	public List<Map> getOrderSettingByMonth(String date) {
		// TODO Auto-generated method stub
		//加后缀
		String dateBegin = date + "-01";
		//加后缀
		String dateEnd = date + "-31";
		//用集合存数据
		Map map = new HashMap();
		
		map.put("dateBegin",dateBegin);
		
		map.put("dateEnd",dateEnd);
		
		List<OrderSetting> list = orderSettingMapper.getOrderSettingByMonth(map);

		List<Map> data = new ArrayList<>();
		
		for (OrderSetting orderSetting : list) {
			Map orderSettingMap = new HashMap();
			orderSettingMap.put("date",orderSetting.getOrderDate().getDate());//
			orderSettingMap.put("number",orderSetting.getNumber());//可预约人数
			orderSettingMap.put("reservations",orderSetting.getReservations());//
			data.add(orderSettingMap);
		}
		return data;
	}
	//根据日期修改可预约人数
	public void editNumberByDate(OrderSetting orderSetting) {
		long count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
		if(count > 0){
			//当前日期已经进行了预约设置，需要进行修改操作
			orderSettingMapper.editNumberByOrderDate(orderSetting);
		}else{
			//当前日期没有进行预约设置，进行添加操作
			orderSettingMapper.add(orderSetting);
		}
	}
}
