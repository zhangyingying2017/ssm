package cn.edu.zzuli.common.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

public class DateConverterUtils implements Converter {

	public Object convert(Class type, Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Date) {
			return value;
		}
		if (value instanceof Long) {
			Long longValue = (Long) value;
			return new Date(longValue.longValue());
		}
		if (value instanceof String) {
			if (StringUtils.isBlank((String) value)) {
				return null;
			}
			Date endTime = null;

			try {
				endTime = DateUtils.parseDate(value.toString(), new String[] {
						"yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss",
						"yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM-dd" });
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return endTime;
		}
		return null;
	}
	/**
	 * 判断参数是否等于null或者空
	 * 
	 * @param param
	 * @return
	 */
	public static boolean checkParam(Object param) {
		if (null == param || "".equals(param)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 日期天数增加
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		if (checkParam(date)) {
			return null;
		}
		if (0 == days) {
			return date;
		}
		Locale locale = Locale.getDefault();
		Calendar calendar = new GregorianCalendar(locale);
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}

}
