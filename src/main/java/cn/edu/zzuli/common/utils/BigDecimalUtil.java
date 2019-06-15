package cn.edu.zzuli.common.utils;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数值计算工具类，包括加减乘除和四舍五入等方法 *   
 * <p>创建日期：2015年7月25日 </p>
 * @version V1.0  
 * @author luyj
 * @see
 */
public class BigDecimalUtil {
	private static Logger logger = LoggerFactory
			.getLogger(BigDecimalUtil.class);
	/**
	 * 加法
	 * 
	 * <p>创建人：luyj ,  2015年7月25日  下午11:56:24</p>
	 * <p>修改人：luyj ,  2015年7月25日  下午11:56:24</p>
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
		if (v1 == null || v2 == null) {
			return BigDecimal.ZERO;
		}
		return v1.add(v2);
	}
	
	/**
	 * 多个数值相加
	 * 
	 * <p>创建人：luyj ,  2015年8月12日  上午11:20:18</p>
	 * <p>修改人：luyj ,  2015年8月12日  上午11:20:18</p>
	 * @param args
	 * @return
	 */
	public static BigDecimal add(BigDecimal...args) {
		if (args == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal result = BigDecimal.ZERO;
		for (int i = 0; i < args.length; i++) {
			result = result.add(args[i]);
		}
		return result;
	}


	/**
	 * @method add <br>
	 * @Description 将两个字符串类型的数值转为BigDecimal并进行加法运算 <br>
	 * @author zhuyz <br>
	 * @email 2361883887@qq.com<br>
	 * @date 2015年8月24日 下午4:05:13
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal add(String v1, String v2) {
		BigDecimal v1Decimal = str2BigDecimal(v1);
		BigDecimal v2Decimal = str2BigDecimal(v2);
		return add(v1Decimal, v2Decimal);
	}
	
	/**
	 * @method add <br>
	 * @Description 将字符串类型的数值转为BigDecimal并进行加法运算<br>
	 * @author zhuyz <br>
	 * @email 2361883887@qq.com<br>
	 * @date 2015年8月24日 下午4:21:15
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal add(BigDecimal v1, String v2) {
		BigDecimal v2Decimal = str2BigDecimal(v2);
		return add(v1, v2Decimal);
	}
	
	/**
	 * 减法
	 * 
	 * <p>创建人：luyj ,  2015年7月26日  上午12:00:14</p>
	 * <p>修改人：luyj ,  2015年7月26日  上午12:00:14</p>
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static BigDecimal sub(String v1, BigDecimal v2) {
		BigDecimal v1Decimal = str2BigDecimal(v1);
		return add(v1Decimal, v2);
	}
	
	/**
	 * 减法
	 * 
	 * <p>创建人：luyj ,  2015年7月26日  上午12:00:14</p>
	 * <p>修改人：luyj ,  2015年7月26日  上午12:00:14</p>
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
		if (v1 == null || v2 == null) {
			return BigDecimal.ZERO;
		}
		return v1.subtract(v2);
	}

	/**
	 * @method sub <br>
	 * @Description 将两个字符串类型的数值转为BigDecimal并进行减法运算 <br>
	 * @author zhuyz <br>
	 * @email 2361883887@qq.com<br>
	 * @date 2015年8月24日 下午3:50:31
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal sub(String v1, String v2) {
		BigDecimal v1Decimal = str2BigDecimal(v1);
		BigDecimal v2Decimal = str2BigDecimal(v2);
		return sub(v1Decimal, v2Decimal);
	}
	
	/**
	 * 乘法
	 * 
	 * <p>创建人：luyj ,  2015年7月26日  上午12:01:16</p>
	 * <p>修改人：luyj ,  2015年7月26日  上午12:01:16</p>
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
		if (v1 == null || v2 == null) {
			return BigDecimal.ZERO;
		}
		return v1.multiply(v2);
	}

	/**
	 * @method mul <br>
	 * @Description 将String类型数值转为BigDecimal并进行乘法运算 <br>
	 * @author zhuyz <br>
	 * @email 2361883887@qq.com<br>
	 * @date 2015年8月24日 下午5:24:53
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal mul(String v1, BigDecimal v2) {
		BigDecimal v1Decimal = str2BigDecimal(v1);
		return mul(v1Decimal,v2);
	}
	
	/**
	 * @method mul <br>
	 * @Description 多个BigDecimal类型的参数相乘<br>
	 * @author zhuyz <br>
	 * @email 2361883887@qq.com<br>
	 * @date 2015年8月19日 下午5:54:39
	 * @param v
	 * @return
	 */
	public static BigDecimal mul(BigDecimal... v) {
		if (v == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal rtnResult = BigDecimal.ONE;
		for (int i = 0; i < v.length; i++) {
			rtnResult = mul(rtnResult, v[i]);
		}
		return rtnResult;
	}

	/**
	 * @method mul <br>
	 * @Description 多个String类型的数值相乘 <br>
	 * @author zhuyz <br>
	 * @email 2361883887@qq.com<br>
	 * @date 2015年8月26日 上午10:25:16
	 * @param v
	 * @return
	 */
	public static BigDecimal mul(String... v) {
		if (v == null) {
			logger.info("传入参数值为null");
			return BigDecimal.ZERO;
		}
		logger.info("连乘参数数量为:"+v.length);
		BigDecimal bd[] = new BigDecimal[v.length];
		for (int i = 0; i < v.length; i++) {
			logger.info("参数分别为:"+v[i]);
			bd[i] = str2BigDecimal(v[i]);
			if (bd[i] == null) {
				return BigDecimal.ZERO;
			}
		}
		return mul(bd);
	}
	
	/**
	 * 除法
	 * 
	 * 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。  
     * 
	 * <p>创建人：luyj ,  2015年7月26日  上午12:03:51</p>
	 * <p>修改人：luyj ,  2015年7月26日  上午12:03:51</p>
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位
	 * @return 两个参数的商
	 */
	public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
		if (v1 == null || v2 == null || scale < 0
				|| v2.compareTo(BigDecimal.ZERO) == 0) {
			return BigDecimal.ZERO;
		}
		return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 四舍五入
	 * 
	 * <p>创建人：luyj ,  2015年7月26日  上午12:06:13</p>
	 * <p>修改人：luyj ,  2015年7月26日  上午12:06:13</p>
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static BigDecimal round(BigDecimal v, int scale) {
		if (v == null || scale < 0) {
			return BigDecimal.ZERO;
		}
		return v.divide(BigDecimal.ONE, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 返回两个数中大的一个值
	 * 
	 * <p>创建人：luyj ,  2015年7月26日  上午12:08:32</p>
	 * <p>修改人：luyj ,  2015年7月26日  上午12:08:32</p>
	 * @param v1 需要被对比的第一个数
	 * @param v2 需要被对比的第二个数
	 * @return 返回两个数中大的一个值
	 */
	public static BigDecimal returnMax(BigDecimal v1, BigDecimal v2) {
		if (v1 == null || v2 == null) {
			return BigDecimal.ZERO;
		}
		return v1.max(v2);
	}
	
	/**
	 * 返回两个数中小的一个值
	 * 
	 * <p>创建人：luyj ,  2015年7月26日  上午12:08:32</p>
	 * <p>修改人：luyj ,  2015年7月26日  上午12:08:32</p>
	 * @param v1 需要被对比的第一个数
	 * @param v2 需要被对比的第二个数
	 * @return 返回两个数中小的一个值
	 */
	public static BigDecimal returnMin(BigDecimal v1, BigDecimal v2) {
		if (v1 == null || v2 == null) {
			return BigDecimal.ZERO;
		}
		return v1.min(v2);
	}
	
	/**
	 * 比较两个数值大小
	 * 
	 * <p>创建人：luyj ,  2015年7月26日  上午12:11:54</p>
	 * <p>修改人：luyj ,  2015年7月26日  上午12:11:54</p>
	 * @param v1 需要被对比的第一个数 
	 * @param v2 需要被对比的第二个数
	 * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
	 */
	public static int compareTo(BigDecimal v1, BigDecimal v2) {
		if (v1 == null || v2 == null) {
			return -1;
		}
		return v1.compareTo(v2);
	}
	public static void main(String[] args) {
		
		System.out.println(compareTo(new BigDecimal(0.5),new BigDecimal(0.50)));
		System.out.println(sub(new BigDecimal(0.5),new BigDecimal(-1.0)));
	}
	
	/**
	 * 判断是否为正数
	 * 
	 * <p>创建人：luyj ,  2015年7月26日  上午12:11:54</p>
	 * <p>修改人：luyj ,  2015年7月26日  上午12:11:54</p>
	 * @param v 需要判断的数值 
	 * @return 返回判断结果
	 */
	public static boolean isPositive(BigDecimal v) {
		if (v == null) {
			return false;
		}
		if (v.compareTo(BigDecimal.ZERO) > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否为负数
	 * 
	 * <p>创建人：luyj ,  2015年7月26日  上午12:11:54</p>
	 * <p>修改人：luyj ,  2015年7月26日  上午12:11:54</p>
	 * @param v 需要判断的数值 
	 * @return 返回判断结果
	 */
	public static boolean isNegative(BigDecimal v) {
		if (v == null) {
			return false;
		}
		if (v.compareTo(BigDecimal.ZERO) < 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否等于0
	 * 
	 * <p>创建人：luyj ,  2015年7月26日  上午12:11:54</p>
	 * <p>修改人：luyj ,  2015年7月26日  上午12:11:54</p>
	 * @param v 需要判断的数值 
	 * @return 返回判断结果
	 */
	public static boolean equalZero(BigDecimal v) {
		if (v == null) {
			return false;
		}
		if (v.compareTo(BigDecimal.ZERO) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为数值
	 * 
	 * <p>创建人：luyj ,  2015年8月15日  上午12:08:35</p>
	 * <p>修改人：luyj ,  2015年8月15日  上午12:08:35</p>
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		try {
			new BigDecimal(str);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * @method compareTo <br>
	 * @Description 比较两个数是否相等<br>
	 * @author zhuyz <br>
	 * @email 2361883887@qq.com<br>
	 * @date 2015年8月20日 下午5:29:21
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static boolean isSame(BigDecimal v1, BigDecimal v2) {
		if (v1 == null || v2 == null) {
			return false;
		}
		return v1.compareTo(v2) == 0  ? true : false;
	}
	
	/**
	 * @method isSame <br>
	 * @Description 比较两个数是否相等 <br>
	 * @author zhuyz <br>
	 * @email 2361883887@qq.com<br>
	 * @date 2015年8月24日 下午4:06:41
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static boolean isSame(BigDecimal v1, String v2) {
		BigDecimal v2Decimal = str2BigDecimal(v2);
		return isSame(v1, v2Decimal);
	}
	
	/**
	 * @method isSame <br>
	 * @Description 比较两个数是否相等  <br>
	 * @author zhuyz <br>
	 * @email 2361883887@qq.com<br>
	 * @date 2015年9月7日 下午2:22:34
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static boolean isSame(String v1, String v2) {
		BigDecimal v1Decimal = str2BigDecimal(v1);
		return isSame(v1Decimal, v2);
	}
	
	/**
	 * @method percent2Decimal <br>
	 * @Description 将String类型的数值除以100并保留指定位数,返回String类型数值 <br>
	 * 				 非法参数将返回null
	 * @author zhuyz <br>
	 * @email 2361883887@qq.com<br>
	 * @date 2015年8月26日 上午10:42:07
	 * @param v 
	 * @param scale
	 * @return
	 */
	public static String percentStr2DecimalStr(String v,int scale) {
		BigDecimal percentStr = str2BigDecimal(v);
		BigDecimal divide = percentStr2Decimal(percentStr, scale);
		return divide.toString();
	}
	
	/**
	 * @method percentStr2Decimal <br>
	 * @Description 将BigDecimal类型的数值除以100并保留指定位数,返回BigDecimal类型数值 <br>
	 * 				 非法参数将返回null <br>
	 * @author zhuyz <br>
	 * @email 2361883887@qq.com<br>
	 * @date 2015年9月9日 下午2:36:24
	 * @param v
	 * @param scale
	 * @return
	 */
	public static BigDecimal percentStr2Decimal(BigDecimal v,int scale) {
		if (v == null) {
			return null;
		}
		BigDecimal divide = v.divide(new BigDecimal("100"), scale, BigDecimal.ROUND_HALF_UP);
		return divide;
	}
	
	/**
	 * @method decimal2PercentStr <br>
	 * @Description 将小数转为百分数去除百分号形式 (0.03 --> 3) <br>
	 * @author zhuyz <br>
	 * @email 2361883887@qq.com<br>
	 * @date 2016年3月2日 上午11:47:24
	 * @param v
	 * @return
	 */
	public static String decimal2PercentStr(String v) {
		String mul = BigDecimalUtil.mul(v,"100").toString();
		return mul;
	}
	
	/**
	 * @method str2BigDecimal <br>
	 * @Description 将字符串转换为BigDecimal<br>
	 * @author zhuyz <br>
	 * @email 2361883887@qq.com<br>
	 * @date 2015年8月24日 下午3:56:54
	 * @param str
	 * @return
	 */
	public static BigDecimal str2BigDecimal(String str){
		BigDecimal bigDecimal = null;
		try {
			bigDecimal = new BigDecimal(str);
		} catch (Exception e) {
			logger.info("str2BigDecimal异常,传入参数:"+str);
			return null;
		}
		return bigDecimal;
	}
	
	/**
	 * 
	 * @method: calNumber
	 * @Decscription: A*B/c
	 * @author: liutengteng
	 * @date: 2016年4月5日
	 * @param str1
	 * @param str2
	 * @param str3
	 * @param scale 精度
	 * @return
	 */
	public static BigDecimal calNumber(String str1,String str2,String str3,int scale){
		BigDecimal v1 = str2BigDecimal(str1);
		BigDecimal v2 = str2BigDecimal(str2);
		BigDecimal v3 = str2BigDecimal(str3);
		BigDecimal v = div(mul(v1,v2), v3, scale);
		
		return v;
	}
	/**
	 * 
	 * @method: amount
	 * @Decscription: 计算费用 A*B*C/D/E
	 * @author: liutengteng
	 * @date: 2016年4月5日
	 * @param v1
	 * @param v2
	 * @param v3
	 * @param v4
	 * @param v5
	 * @param scale
	 * @return
	 */
	public static BigDecimal amount(BigDecimal v1,BigDecimal v2,BigDecimal v3,BigDecimal v4,BigDecimal v5,int scale1,int scale2){
		BigDecimal b1 = mul(v1,v2);
		BigDecimal b2 = mul(b1,v3);
		BigDecimal b3 = div(b2,v4,scale1);
		BigDecimal b4 = div(b3,v5,scale2);
		
		return b4;
		
	}
}
