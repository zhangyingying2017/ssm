package cn.edu.zzuli.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;

public class BeanConvertUtils {

	private final static String df_datetime = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 支持前台时间类型到后台的转换
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */

	public static void frxConvertByDateTime(Object bean, Map properties)
			throws IllegalAccessException, InvocationTargetException {
		DateConverterUtils dateConverterUtils = new DateConverterUtils();
		//dtConverter.setPattern(df_datetime);
		ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
		convertUtilsBean.deregister(Date.class);
		convertUtilsBean.register(dateConverterUtils, Date.class);

		BeanUtilsBean beanUtilsBean = new BeanUtilsBean(convertUtilsBean,
				new PropertyUtilsBean());
		beanUtilsBean.populate(bean, properties);
	}

	public static String beanToString(Object obj) {
		JSONObject job = JSONObject.fromObject(obj);
		return job.toString();
	}	
}
