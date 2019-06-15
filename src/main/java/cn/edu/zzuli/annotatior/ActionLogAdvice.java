package cn.edu.zzuli.annotatior;



import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;

import cn.edu.zzuli.common.utils.ActionLogHelper;

/**
 * Created by lee on 15/9/14.
 */
public class ActionLogAdvice implements MethodInterceptor {

  /**
   * invoke
   */
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    ActionLog data = invocation.getMethod().getAnnotation(ActionLog.class);
    if (data == null) {
      return invocation.proceed();
    }
    String methodName = invocation.getMethod().getName();
    String serviceFullName = AopUtils.getTargetClass(invocation.getThis()).getName();
    long startTime = 0;
    long endTime = 0;
    long costTime = 0;
    Object result = null;
    try {
      startTime = System.currentTimeMillis();
      ActionLogHelper.start(methodName, serviceFullName);
      result = invocation.proceed();
      endTime = System.currentTimeMillis();
      costTime = endTime - startTime;
      ActionLogHelper.stop(methodName, serviceFullName, costTime);
    } catch (Throwable e) {
      endTime = System.currentTimeMillis();
      costTime = endTime - startTime;
      ActionLogHelper.exception(methodName, serviceFullName, costTime,(Exception)e);
      
      throw e;
    }
    return result;
  }

}
