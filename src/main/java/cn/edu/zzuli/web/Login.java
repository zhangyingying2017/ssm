package cn.edu.zzuli.web;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
@SuppressWarnings("all")
public class Login {

	//@Resource
	//private RedisTemplate< String, String> redisTemplate;
	
	private final static Logger logger = Logger.getLogger(Login.class);
	
	@RequestMapping(value = { "/login" }, method = { RequestMethod.GET })
	public void  login(){
		logger.info("***************进入登录方法*********************");
		
//		redisTemplate.opsForValue().set("user", "123");
//		
//		String user = redisTemplate.opsForValue().get("user");
//		
//		System.out.println(user  + " ----->登陆成功");
		
		logger.info("***************登录方法响应结束*********************");
	}
}
