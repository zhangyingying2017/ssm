package cn.edu.zzuli.common.utils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.Converter;

public class DateUtils implements Converter{
	private final static SimpleDateFormat df_datetime = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	private final static SimpleDateFormat df_date = new SimpleDateFormat(
			"yyyyMMdd");

	private final static SimpleDateFormat df_date10 = new SimpleDateFormat(
			"yyyy-MM-dd");
	private final static SimpleDateFormat df_date20 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat df_date30 = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");
	private final static SimpleDateFormat df_date40 = new SimpleDateFormat(
			"yyyyMMddHHmmss_SSS");
	private final static SimpleDateFormat df_hour = new SimpleDateFormat(
			"HH:mm:ss");
	
	private static final String DATE      = "yyyy-MM-dd";
	private static final String DATETIME  = "yyyy-MM-dd HH:mm:ss";
	private static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";
	
	public static String getString30(Date date) {
		if (date != null) {
			return df_date30.format(date);
		} else {
			return null;
		}
	}
	public static String getString40(Date date) {
		if (date != null) {
			return df_date40.format(date);
		} else {
			return null;
		}
	}
	
	public static String getString20(Date date) {
		if (date != null) {
			return df_date20.format(date);
		} else {
			return null;
		}
	}
	public static String getDateTime(Date date) {
		if (date != null) {
			return df_datetime.format(date);
		} else {
			return null;
		}
	}

	public static Date getDateTime(String dtsrt) {
		try {
			return df_datetime.parse(dtsrt);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String getDate(Date date) {
		if (date != null) {
			return df_date.format(date);
		} else {
			return null;
		}
	}

	public static String getDate10(Date date) {
		if (date != null) {
			return df_date10.format(date);
		} else {
			return null;
		}
	}
	
	public static Date getStringToDate10(String str) throws ParseException {
		if (str != null) {
			return df_date10.parse(str);
		} else {
			return null;
		}
	}

	public static Date getDate(String dtsrt) {
		try {
			return df_date.parse(dtsrt);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static Date getDate2(String dtsrt){
		try {
			return df_date10.parse(dtsrt);
		} catch (ParseException e) {
			return null;
		}
	}
	public static Date getDate3(String dtsrt){
		try {
			return df_date20.parse(dtsrt);
		} catch (ParseException e) {
			return null;
		}
	}	
	
	public static boolean verifyTime(String startTime, String endTime) {
		if (startTime != null && endTime != null) {
			String curDateTime = getDateTime(new Date());
			if (curDateTime != null) {
				if (curDateTime.compareTo(startTime) >= 0
						&& curDateTime.compareTo(endTime) <= 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	@Override
    public Object convert(Class type, Object value) {
        return toDate(type, value);
    }
 
    public static Object toDate(Class type, Object value) {
        if (value == null || "".equals(value))
            return null;
        if (value instanceof String) {
            String dateValue = value.toString().trim();
            int length = dateValue.length();
            if (type.equals(java.util.Date.class)) {
                try {
                    DateFormat formatter = null;
                    if (length <= 10) {
                        formatter = new SimpleDateFormat(DATE, new DateFormatSymbols(Locale.CHINA));
                        return formatter.parse(dateValue);
                    }
                    if (length <= 19) {
                        formatter = new SimpleDateFormat(DATETIME, new DateFormatSymbols(Locale.CHINA));
                        return formatter.parse(dateValue);
                    }
                    if (length <= 23) {
                        formatter = new SimpleDateFormat(TIMESTAMP, new DateFormatSymbols(Locale.CHINA));
                        return formatter.parse(dateValue);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }
    
    /**
     * 计算两个时间之间的天数
     * @param smdate 较小的时间
     * @param bdate 较大的时间 
     * @return 相差天数 
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException  {    
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        smdate = sdf.parse(sdf.format(smdate));  
        bdate = sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days = (time2-time1) / (1000*3600*24);  
        return Integer.parseInt(String.valueOf(between_days));           
    }   
    
    /**
     * 根据指定时间获取星期
     * @param date 指定时间 
     * @return 星期
     */
    public static String getWeekName(Date date){    
              String[] weeks = {"星期六","星期日","星期一","星期二","星期三","星期四","星期五"};
              Calendar cal = Calendar.getInstance();
              cal.setTime(date);
              int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
              return weeks[week];
    } 
    
    /**
     * 判断当前为上午还是下午
     * @param  
     * @return time
     */
    public static Boolean judgecurrent (Date date){ 
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString12 = formatter.format(date) +  " 12:00:00";
		
		
    	
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.get(Calendar.HOUR_OF_DAY);
        Map<Integer,String> map = new HashMap<Integer,String>();
    	if(calendar.get(Calendar.HOUR_OF_DAY) <=12){
    		return true;
    	}else{
    		return false;
    	}
    }   
    
    /**
     *12点
     * @param  
     * @return time
     */
    public static String noon (Date date){ 
		String dateString12 = getDate10(date) +  " 12:00:00";
    	return dateString12;
    }   
    
    /**
     * 0点
     * @param  
     * @return time
     */
    public static String morning (Date date){ 
    	String dateString00 = getDate10(date) +  " 00:00:00";
    	return dateString00;
    }   
    
    /**
     * 24点
     * @param  
     * @return time
     */
    public static String night (Date date){
    	String dateString24 = getDate10(date) +  " 23:59:59";
    	return dateString24;
    }  
    
    public static String getStringhour(Date date) {
		if (date != null) {
			return df_hour.format(date);
		} else {
			return null;
		}
	}
}
