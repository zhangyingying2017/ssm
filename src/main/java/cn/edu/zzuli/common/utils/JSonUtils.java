package cn.edu.zzuli.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSonUtils {
	static ObjectMapper objectMapper;
	private static Logger loger = LoggerFactory.getLogger(JSonUtils.class);

	/**
	 * 
	 * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
	 * 
	 * (1)转换为普通JavaBean：readValue(json,Student.class)
	 * 
	 * (2)转换为List:readValue(json,List.class).但是如果我们想把json转换为特定类型的List，比如List<
	 * Student>，就不能直接进行转换了。
	 * 
	 * 因为readValue(json,List.class)返回的其实是List<Map>类型，你不能指定readValue()的第二个参数是List
	 * <Student>.class，所以不能直接转换。
	 * 
	 * 我们可以把readValue()的第二个参数传递为Student[].class.然后使用Arrays.asList();
	 * 方法把得到的数组转换为特定类型的List。
	 * 
	 * (3)转换为Map：readValue(json,Map.class)
	 * 
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我们使用泛型，得到的也是泛型
	 * 
	 * 
	 * 
	 * @param content
	 *            要转换的JavaBean类型
	 * 
	 * @param valueType
	 *            原始json字符串数据
	 * 
	 * @return JavaBean对象
	 */
	public static <T> T readValue(String content, Class<T> valueType) {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		try {
			// objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,
			// true) ;
			return objectMapper.readValue(content, valueType);
		} catch (Exception e) {
			loger.warn(e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * 把JavaBean转换为json字符串
	 * 
	 * (1)普通对象转换：toJson(Student)
	 * 
	 * (2)List转换：toJson(List)
	 * 
	 * (3)Map转换:toJson(Map)
	 * 
	 * 我们发现不管什么类型，都可以直接传入这个方法
	 * 
	 * 
	 * 
	 * @param object
	 *            JavaBean对象
	 * 
	 * @return json字符串
	 */
	public static String toJSon(Object object) {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			loger.warn(e.getMessage());
		}
		return null;
	}

	/**
	 * 从json串中取得json对象
	 * 
	 * @param jsob
	 * @param key
	 * @return
	 */
	public static JSONObject getJSONObject(JSONObject jsob, String key) {
		JSONObject retJsob = null;
		try {
			if (jsob != null && StringUtils.isNotEmpty(key)) {
				if (jsob.has(key)) {
					retJsob = jsob.getJSONObject(key);
				}
			}
		} catch (Exception e) {
			loger.warn(e.getMessage());
		}
		return retJsob;
	}

	/**
	 * 从json串中取得String
	 * 
	 * @param jsob
	 * @param key
	 * @return
	 */
	public static String getString(JSONObject jsob, String key) {
		String retJsob = null;
		try {
			if (jsob != null && StringUtils.isNotEmpty(key)) {
				if (jsob.has(key)) {
					retJsob = jsob.getString(key);
				}
			}
		} catch (Exception e) {
			loger.warn(e.getMessage());
		}
		return retJsob;
	}

	/**
	 * 从json串中取得double
	 * 
	 * @param jsob
	 * @param key
	 * @return
	 */
	public static Double getDouble(JSONObject jsob, String key) {
		Double retJsob = null;
		try {
			if (jsob != null && StringUtils.isNotEmpty(key)) {
				if (jsob.has(key)) {
					retJsob = jsob.getDouble(key);
				}
			}
		} catch (Exception e) {
			loger.warn(e.getMessage());
		}
		return retJsob;
	}

	/**
	 * 从json串中取得Object
	 * 
	 * @param jsob
	 * @param key
	 * @return
	 */
	public static Object getObject(JSONObject jsob, String key, String MethodName) {
		Object retJsob = null;
		try {
			if (jsob != null && StringUtils.isNotEmpty(key)) {
				if (jsob.has(key)) {
					retJsob = execute(jsob, JSONObject.class.getName(), MethodName, key);
				}
			}
		} catch (Exception e) {
			loger.warn(e.getMessage());
		}
		return retJsob;
	}

	/**
	 * 
	 * @param obj
	 *            待执行方法的对象
	 * @param ClassName
	 *            待执行方法对象的类
	 * @param MethodName
	 *            待执行的方法名
	 * @param ParameterValue
	 *            待执行方法的参数
	 */
	public static Object execute(Object obj, String ClassName, String MethodName, String ParameterValue) {
		Class<?> cls = null;
		try {
			cls = Class.forName(ClassName);
		} catch (ClassNotFoundException e) {
			// 通过ClassName反射获取该类失败
			loger.warn(e.getMessage());
		}
		Method method = null;
		try {
			method = cls.getMethod(MethodName, String.class);
		} catch (SecurityException e) {
			// 通过MethodName反射获取该方法失败，SecurityManager校验失败
			loger.warn(e.getMessage());
		} catch (NoSuchMethodException e) {
			// 通过MethodName反射获取该方法失败，该方法不存在
			loger.warn(e.getMessage());
		}
		try {
			return method.invoke(obj, ParameterValue);
		} catch (IllegalArgumentException e) {
			// 反射执行该方法失败，参数不正确
			loger.warn(e.getMessage());
		} catch (IllegalAccessException e) {
			// 反射执行该方法失败，无法执行
			loger.warn(e.getMessage());
		} catch (InvocationTargetException e) {
			// 反射执行该方法失败，该方法本身抛出异常
			loger.warn(e.getMessage());
		}
		return null;
	}
}
