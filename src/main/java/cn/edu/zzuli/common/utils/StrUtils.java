package cn.edu.zzuli.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;



/**
 * 字符串工具类
 */
public class StrUtils implements java.io.Serializable {
	

//	/**
//	 * 编码
//	 * @param bstr
//	 * @return
//	 */
//	public static String encode(byte[] bstr) {
//		return new sun.misc.BASE64Encoder().encode(bstr);
//	}
//
//	/**
//	 * 解码
//	 * @param str
//	 * @return string
//	 */
//	public static byte[] decode(String str) {
//		byte[] bt = null;
//		try {
//			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
//			bt = decoder.decodeBuffer(str);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return bt;
//	}  
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2534225583906610267L;

	/**
	 * 编码
	 * @param bstr
	 * @return
	 */
	public static String encode(byte[] bstr) {
		return new String(Base64.encodeBase64(bstr));  
	}

	/**
	 * 解码
	 * @param str
	 * @return string
	 */
	public static byte[] decode(byte[] str) {
		return Base64.decodeBase64(str);  
	}  
	
	
	/**
	 * 将字符串转化为URL编码
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String strToDecoder(String str) throws UnsupportedEncodingException {
		return URLDecoder.decode(str, "UTF-8");
	}
	
	/**
	 * 将URL编码转化为字符串
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String strToEncoder(String str) throws UnsupportedEncodingException {
		return URLEncoder.encode(str,  "UTF-8");
	}
	
	

	
	
	public static void main(String[] arge) throws UnsupportedEncodingException {
		
//		System.out.println(strToEncoder("二维码无效!"));
		//System.out.println(StrUtils.getRandom());
//		try {
//			
//			System.out.println("AuthCode: >>" + json.get("AuthCode"));
//			System.out.println("UniqueIdentifier: >>" + json.get("UniqueIdentifier"));
//			System.out.println("Mobile: >>" + json.get("Mobile"));
//			System.out.println("CheckCode: >>" + json.get("CheckCode"));
//		} catch (JSONException e) {
//			
//		}
		
	}
	
	/**
     * 使用正则表达式
     * 
     * @author        Cha
     * @param reg
     * @param string
     * @return
     */
    public  static boolean startCheck(String reg,String string)  
    {  
        boolean tem=false;  
          
        Pattern pattern = Pattern.compile(reg);  
        Matcher matcher=pattern.matcher(string);  
          
        tem=matcher.matches();  
        return tem;  
    }
}
