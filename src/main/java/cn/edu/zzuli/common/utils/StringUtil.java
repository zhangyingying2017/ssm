package cn.edu.zzuli.common.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * 匹配规则:数字或字母
	 */
	public static final String NUM_OR_LETTER_REGEX = "[\\da-zA-Z]";

	/**
	 * 判断字符串是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (s == null || "".equals(s.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * @Title: StringUtil.java
	 * @Description: 判断线性表是否为空
	 * @author Administrator
	 * @date 2015年12月29日 下午3:19:30
	 */
	public static boolean isEmpty(Collection<?> list) {
		if (list == null || list.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * NULL字符串转换，参数对象为null时，返回空字符串
	 * 
	 * @param o
	 *            转换原对象
	 * @return 字符串
	 */
	public static String nvl(Object o) {
		if (o == null) {
			return "";
		}
		return o.toString().trim();
	}

	public static String getUUID() {
		String str = UUID.randomUUID().toString().replaceAll("-", "");
		return str;
	}

	/**
	 * @Description：检测参数
	 * @Param:required是否必填(true 必填 false 非必填) maxLength 最大长度 minLength 最小长度
	 *                          classType
	 *                          参数类型(String,Integer,Float,Double,BigDecimal)
	 *                          value 值
	 * @Return:true 符合 false 不符合
	 * @Author:wjy
	 * @Date:2016-4-18 09:34:42
	 */
	public static boolean checkData(boolean required, int maxLength, int minLength, Class<?> classType, String value) {
		boolean result = true;
		if (required == true) {
			if (null == value || "".equals(value.trim())) {
				result = false;
				return result;
			}
		}
		if (maxLength <= 0 || minLength < 0 || maxLength < minLength) {
			result = false;
			return result;
		}
		if (!(classType.equals(String.class) || classType.equals(Integer.class) || classType.equals(Float.class)
				|| classType.equals(Double.class) || classType.equals(BigDecimal.class))) {
			result = false;
			return result;
		}
		if (value.length() < minLength || value.length() > maxLength) {
			result = false;
			return result;
		}
		if (classType.equals(String.class)) {
			Pattern pat = Pattern.compile(StringUtil.NUM_OR_LETTER_REGEX + "{" + minLength + "," + maxLength + "}");
			Matcher mat = pat.matcher(value);
			if (!mat.matches()) {
				result = false;
				return result;
			}
		}
		if (classType.equals(Integer.class)) {
			try {
				Integer.parseInt(value);
			} catch (Exception e) {
				result = false;
				return result;
			}
		}
		if (classType.equals(Float.class)) {
			try {
				Float.parseFloat(value);
			} catch (Exception e) {
				result = false;
				return result;
			}
		}
		if (classType.equals(Double.class)) {
			try {
				Double.parseDouble(value);
			} catch (Exception e) {
				result = false;
				return result;
			}
		}
		if (classType.equals(BigDecimal.class)) {
			try {
				new BigDecimal(value);
			} catch (Exception e) {
				result = false;
				return result;
			}
		}
		return result;
	}

	/**
	 * @Description:手机号校验
	 * @param required(是否是必填项)
	 * @param phone
	 * @return boolean(true是,false不是)
	 * @Author:gukp
	 * @Date:2016年4月18日上午11:10:28
	 * @Version 1.0.0
	 */
	public static boolean isPhone(boolean required, String phone) {
		if (isEmpty(phone) && false == required) {
			return true;
		}
		if (isEmpty(phone) && true == required) {
			return false;
		}
		String regex = "^1[34578]\\d{9}$";
		boolean flag = Pattern.matches(regex, phone);
		return flag;
	}

	/**
	 * @Description:邮箱校验
	 * @param required(是否是必填项)
	 * @param mail
	 * @return boolean(true是,false不是)
	 * @Author:gukp
	 * @Date:2016年4月18日上午11:11:21
	 * @Version 1.0.0
	 */
	public static boolean isMail(boolean required, String mail) {
		if (isEmpty(mail) && false == required) {
			return true;
		}
		if (isEmpty(mail) && true == required) {
			return false;
		}
		String regex = "^[a-z\\d]+(\\.[a-z\\d]+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$";
		boolean flag = Pattern.matches(regex, mail);
		return flag;
	}

	/**
	 * @Description:日期校验
	 * @param required(是否是必填项)
	 * @param date
	 * @return boolean(true是,false不是)
	 * @Author:gukp
	 * @Date:2016年4月18日上午11:11:35
	 * @Version 1.0.0
	 */
	public static boolean isDate(boolean required, Date date) {
		if (null == date && false == required) {
			return true;
		}
		if (null == date && true == required) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		String time = sdf.format(date);
		int year = Integer.parseInt(time.split("-")[0]);
		int month = Integer.parseInt(time.split("-")[1]);
		int day = Integer.parseInt(time.split("-")[2]);
		switch (month) {
		case 1:
			return (31 == day ? true : false);
		case 2:// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
				return (29 == day ? true : false);
			} else {
				return (28 == day ? true : false);
			}
		case 3:
			return (31 == day ? true : false);
		case 4:
			return (30 == day ? true : false);
		case 5:
			return (31 == day ? true : false);
		case 6:
			return (30 == day ? true : false);
		case 7:
			return (31 == day ? true : false);
		case 8:
			return (31 == day ? true : false);
		case 9:
			return (30 == day ? true : false);
		case 10:
			return (31 == day ? true : false);
		case 11:
			return (30 == day ? true : false);
		case 12:
			return (31 == day ? true : false);
		default:
			return false;
		}
	}

	/**
	 * 随机生成6位验证码
	 * 
	 * @author guowenjign
	 * @date: 2016-04-18
	 */
	public static String getRandCode() {
		String s_Rand = "";
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			String rand = String.valueOf(random.nextInt(10));
			s_Rand += rand;
		}
		return s_Rand;
	}

	/**
	 * 
	 * @Description：校验文本长度（中文数字字母都一样） @Param:@param maxLength
	 * @Param:@param minLength
	 * @Param:@param value
	 * @Param:@return
	 * @Return: boolean
	 * @Author: daishun
	 * @Date: 2016年4月27日
	 */
	public static boolean checkText(int maxLength, int minLength, String value) {
		if (value == null || "".equals(value) || value.length() > maxLength || value.length() < minLength)
			return false;
		return true;
	}

	/**
	 * 
	 * @Description：去除字符串中的空字符
	 * @Param:String
	 * @Return: String
	 * @Author: zsp
	 * @Date: 2017年9月26日
	 */
	public static String checkNullCharacters(String string) {
		String result = string.replaceAll(" ", "");
		return result;
	}

	public static Map<String, String> mapStringToMap(String str) {
		str = str.substring(1, str.length() - 1);
		String[] strs = str.split(",");
		Map<String, String> map = new HashMap<String, String>();
		for (String string : strs) {
			String key = string.split("=")[0];
			String value = string.split("=")[1];
			value = value.replaceAll("\\|", ",");
			map.put(key, value);
		}
		return map;
	}
}
