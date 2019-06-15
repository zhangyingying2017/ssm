package cn.edu.zzuli.common.utils;


import org.apache.log4j.Logger;


public class ActionLogHelper {
	private static Logger log = Logger.getLogger(ActionLogHelper.class);
	
	public static void start(String methodName,String serviceFullName){		
		log.info("serviceFullName:"+serviceFullName+","+"methodName:"+methodName+",start");		
	}
	
	public static void stop(String methodName,String serviceFullName,long costTime){
		log.info("serviceFullName:"+serviceFullName+","+"methodName:"+methodName+",stop");		
		log.info("costTime:"+costTime);		
	}
	
	public static void exception(String methodName,String serviceFullName,long costTime,Exception e){
		log.error("serviceFullName:"+serviceFullName+","+"methodName:"+methodName+",exception");	
		log.error("costTime:"+costTime);
		log.error("e:",e);	
	}	
}
