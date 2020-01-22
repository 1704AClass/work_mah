package com.itheima.jobs;

import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aisile.oss.AliyunOSSClientUtils;
import com.aisile.oss.RedisConstant;

import redis.clients.jedis.JedisPool;

public class ClearImgJob {
	
	@Autowired
	private JedisPool jedisPool;
	@Test
	public void clearImg(){
		//根据Redis中保存的两个set集合进行差值计算，获得垃圾图片名称集合
		Set<String> set =jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);
		if(set != null){
			AliyunOSSClientUtils aliyunOSSClientUtils = new AliyunOSSClientUtils();
			for (String picName : set){
				//删除服务器上的图片
				aliyunOSSClientUtils.deleteImage(picName);
				/*QiniuUtils.deleteFileFromQiniu(picName);*/
				//从Redis集合中删除图片名称
				jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
			}
		}
	}
}
