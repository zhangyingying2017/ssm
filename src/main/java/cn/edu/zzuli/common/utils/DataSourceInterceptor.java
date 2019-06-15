package cn.edu.zzuli.common.utils;

import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.ClassUtils;
import org.springframework.beans.factory.InitializingBean;

public class DataSourceInterceptor implements MethodInterceptor, InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Class<?> clazz = invocation.getThis().getClass();
		String className = clazz.getName();
		if (ClassUtils.isAssignable(clazz, Proxy.class)) {
			className = invocation.getMethod().getDeclaringClass().getName();
		}

		if (className.contains("com.wilson.park.dao2")) {
			DynamicDataSource.setCustomerType(DynamicDataSource.DATA_SOURCE_B);
		} else if (className.contains("com.wilson.park.dao3")) {
			DynamicDataSource.setCustomerType(DynamicDataSource.DATA_SOURCE_C);
		} else {
			DynamicDataSource.setCustomerType(DynamicDataSource.DATA_SOURCE_A);
		}
		Object result = invocation.proceed();
		return result;
	}
}
