package cn.edu.zzuli.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;



public class SpringRequestInterceptor implements HandlerInterceptor {

	public static Logger logger = LoggerFactory.getLogger(SpringRequestInterceptor.class);
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
								Object obj) throws Exception {		
		String reqPara = JSON.toJSONString(request.getParameterMap());
		logger.info("请求url和参数为:"+request.getRequestURI() + "-" + reqPara);
		
    	return true;
	}

}
