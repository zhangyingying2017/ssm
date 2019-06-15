package cn.edu.zzuli.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;


/**
 * 封装了一些采用HttpClient发送HTTP请求的方法
 * 
 * @see 本工具所采用的是最新的HttpComponents-Client-4.2.1
 * @see 关于本工具类中的一些解释说明,可参考下方列出的我的三篇文章
 * @see http://blog.csdn.net/jadyer/article/details/7615830
 * @see http://blog.csdn.net/jadyer/article/details/7615880
 * @see http://blog.csdn.net/jadyer/article/details/7802139
 * @create Feb 1, 2012 3:02:27 PM
 * @update Oct 8, 2012 3:48:55 PM
 * @author 玄玉<http://blog.csdn/net/jadyer>
 * @version v1.3
 * @history v1.0-->新建<code>sendGetRequest(String,String)</code>和
 *          <code>sendPostRequest(String,Map<String,String>,String,String)</code>
 *          方法
 * @history v1.1-->新增
 *          <code>sendPostSSLRequest(String,Map<String,String>,String,String)</code>
 *          方法,用于发送HTTPS的POST请求
 * @history v1.2-->新增
 *          <code>sendPostRequest(String,String,boolean,String,String)</code>
 *          方法,用于发送HTTP协议报文体为任意字符串的POST请求
 * @history v1.3-->新增<code>java.net.HttpURLConnection</code>实现的
 *          <code>sendPostRequestByJava()</code>方法
 */
public class HttpClientUtil {
	
	private final static Logger logger = Logger.getLogger(HttpClientUtil.class);

	private static HttpClientUtil instance = null;

	public static synchronized HttpClientUtil getInstance() {
		if (instance == null) {
			instance = new HttpClientUtil();
		}
		return instance;
	}

	/**
	 * 发送HTTP_GET请求
	 * 
	 * @see 该方法会自动关闭连接,释放资源
	 * @param requestURL
	 *            请求地址(含参数)
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendGetRequest(String reqURL, String decodeCharset) {
		reqURL = reqURL.replaceAll("\\s", "");
		// long responseLength = 0; // 响应长度
		String responseContent = null; // 响应内容
        CloseableHttpClient httpclient = HttpClients.createDefault();// 创建默认的httpClient实例.
		HttpGet httpGet = new HttpGet(reqURL); // 创建org.apache.http.client.methods.HttpGet
		httpGet.addHeader("Authorization","");
		try {
			CloseableHttpResponse response = httpclient.execute(httpGet);  // 执行GET请求
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				// responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity,
						decodeCharset == null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity); // Consume response content
			}
			// System.out.println("请求地址: " + httpGet.getURI());
			// System.out.println("响应状态: " + response.getStatusLine());
			// System.out.println("响应长度: " + responseLength);
			// System.out.println("响应内容: " + responseContent);
		} catch (ClientProtocolException e) {
			// LogUtil.getLogger().error("该异常通常是协议错误导致,比如构造HttpGet对象时传入的协议不对(将'http'写成'htp')或者服务器端返回的内容不符合HTTP协议要求等,堆栈信息如下",
			// e);
			e.printStackTrace();
		} catch (ParseException e) {
			// LogUtil.getLogger().error(e.getMessage(), e);
			e.printStackTrace();
		} catch (IOException e) {
			// LogUtil.getLogger().error("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下",
			// e);
			e.printStackTrace();
		} finally {
			 if (httpclient != null) {  
	                try {  
	                    httpclient.close();  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }   // 关闭连接,释放资源
		}
		return responseContent;
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 该方法为<code>sendPostRequest(String,String,boolean,String,String)</code>
	 *      的简化方法
	 * @see 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 * @see 当<code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][
	 *      ]等特殊字符进行<code>URLEncoder.encode(string,"UTF-8")</code>
	 * @param isEncoder
	 *            用于指明请求数据是否需要UTF-8编码,true为需要
	 */
	public static String sendPostRequest(String reqURL, String sendData,
			boolean isEncoder) {
		return sendPostRequest(reqURL, sendData, isEncoder, null, null);
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 当<code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][
	 *      ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL
	 *            请求地址
	 * @param sendData
	 *            请求参数,若有多个参数则应拼接成param11=value11¶m22=value22¶m33=value33的形式后,
	 *            传入该参数中
	 * @param isEncoder
	 *            请求数据是否需要encodeCharset编码,true为需要
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostRequest(String reqURL, String sendData,
			boolean isEncoder, String encodeCharset, String decodeCharset) {
		String responseContent = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(reqURL);
		// httpPost.setHeader(HTTP.CONTENT_TYPE,
		// "application/x-www-form-urlencoded; charset=UTF-8");
		httpPost.setHeader(HTTP.CONTENT_TYPE,
				"application/x-www-form-urlencoded");
		try {
			if (isEncoder) {
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
				for (String str : sendData.split("&")) {
					formParams.add(new BasicNameValuePair(str.substring(0,
							str.indexOf("=")),
							str.substring(str.indexOf("=") + 1)));
				}
				httpPost.setEntity(new StringEntity(URLEncodedUtils.format(
						formParams, encodeCharset == null ? "UTF-8"
								: encodeCharset)));
			} else {
				httpPost.setEntity(new StringEntity(sendData,"UTF-8"));
			}

			CloseableHttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity,
						decodeCharset == null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			// LogUtil.getLogger().error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下",
			// e);
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }
		}
		return responseContent;
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
	 *      <code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostRequest(String reqURL,
			Map<String, String> params, String encodeCharset,
			String decodeCharset) {
		String responseContent = null;
		CloseableHttpClient httpclient = HttpClients.createDefault(); 

		HttpPost httpPost = new HttpPost(reqURL);
		List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // 创建参数队列
		for (Map.Entry<String, String> entry : params.entrySet()) {
			formParams.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
		}
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(formParams,
					encodeCharset == null ? "UTF-8" : encodeCharset));

			CloseableHttpResponse response = httpclient.execute(httpPost); 
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity,
						decodeCharset == null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			// LogUtil.getLogger().error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下",
			// e);
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }
		}
		return responseContent;
	}

	/**
	 * 发送HTTPS_POST请求
	 * 
	 * @see 该方法为<code>sendPostSSLRequest(String,Map<String,String>,String,String)</code>
	 *      方法的简化方法
	 * @see 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
	 *      <code>URLEncoder.encode(string,"UTF-8")</code>
	 */
	public static String sendPostSSLRequest(String reqURL,
			Map<String, String> params) {
		return sendPostSSLRequest(reqURL, params, null, null);
	}

	/**
	 * 发送HTTPS_POST请求
	 * 
	 * @see 该方法会自动关闭连接,释放资源
	 * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行
	 *      <code>URLEncoder.encode(string,encodeCharset)</code>
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
	 * @param decodeCharset
	 *            解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
	 * @return 远程主机响应正文
	 */
	public static String sendPostSSLRequest(String reqURL,
			Map<String, String> params, String encodeCharset,
			String decodeCharset) {
		String responseContent = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		X509TrustManager xtm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[] { xtm }, null);
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
			httpclient.getConnectionManager().getSchemeRegistry()
					.register(new Scheme("https", 443, socketFactory));

			HttpPost httpPost = new HttpPost(reqURL);
			List<NameValuePair> formParams = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(formParams,
					encodeCharset == null ? "UTF-8" : encodeCharset));

			CloseableHttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity,
						decodeCharset == null ? "UTF-8" : decodeCharset);
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			// LogUtil.getLogger().error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
			e.printStackTrace();
		} finally {
			 // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }
		}
		return responseContent;
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 若发送的<code>params</code>中含有中文,记得按照双方约定的字符集将中文
	 *      <code>URLEncoder.encode(string,encodeCharset)</code>
	 * @see 本方法默认的连接超时时间为30秒,默认的读取超时时间为30秒
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            发送到远程主机的正文数据,其数据类型为<code>java.util.Map<String, String></code>
	 * @return 远程主机响应正文`HTTP状态码,如<code>"SUCCESS`200"</code><br>
	 *         若通信过程中发生异常则返回"Failed`HTTP状态码",如<code>"Failed`500"</code>
	 */
	public static String sendPostRequestByJava(String reqURL,
			Map<String, String> params) {
		StringBuilder sendData = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sendData.append(entry.getKey()).append("=")
					.append(entry.getValue()).append("&");
		}
		if (sendData.length() > 0) {
			sendData.setLength(sendData.length() - 1); // 删除最后一个&符号
		}
		return sendPostRequestByJava(reqURL, sendData.toString());
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 若发送的<code>sendData</code>中含有中文,记得按照双方约定的字符集将中文
	 *      <code>URLEncoder.encode(string,encodeCharset)</code>
	 * @see 本方法默认的连接超时时间为30秒,默认的读取超时时间为30秒
	 * @param reqURL
	 *            请求地址
	 * @param sendData
	 *            发送到远程主机的正文数据
	 * @return 远程主机响应正文`HTTP状态码,如<code>"SUCCESS`200"</code><br>
	 *         若通信过程中发生异常则返回"Failed`HTTP状态码",如<code>"Failed`500"</code>
	 */
	public static String sendPostRequestByJava(String reqURL, String sendData) {
		HttpURLConnection httpURLConnection = null;
		OutputStream out = null; // 写
		InputStream in = null; // 读
		int httpStatusCode = 0; // 远程主机响应的HTTP状态码
		try {
			URL sendUrl = new URL(reqURL);
			httpURLConnection = (HttpURLConnection) sendUrl.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true); // 指示应用程序要将数据写入URL连接,其值默认为false
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setConnectTimeout(30000); // 30秒连接超时
			httpURLConnection.setReadTimeout(30000); // 30秒读取超时

			out = httpURLConnection.getOutputStream();
			out.write(sendData.toString().getBytes());

			// 清空缓冲区,发送数据
			out.flush();

			// 获取HTTP状态码
			httpStatusCode = httpURLConnection.getResponseCode();

			// 该方法只能获取到[HTTP/1.0 200 OK]中的[OK]
			// 若对方响应的正文放在了返回报文的最后一行,则该方法获取不到正文,而只能获取到[OK],稍显遗憾
			// respData = httpURLConnection.getResponseMessage();

			// //处理返回结果
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(httpURLConnection.getInputStream()));
			// String row = null;
			// String respData = "";
			// if((row=br.readLine()) != null){
			// //readLine()方法在读到换行[\n]或回车[\r]时,即认为该行已终止
			// respData = row; //HTTP协议POST方式的最后一行数据为正文数据
			// }
			// br.close();

			in = httpURLConnection.getInputStream();
			byte[] byteDatas = new byte[in.available()];
			in.read(byteDatas);
			return new String(byteDatas) + "`" + httpStatusCode;
		} catch (Exception e) {
			// LogUtil.getLogger().error(e.getMessage());
			e.printStackTrace();
			return "Failed`" + httpStatusCode;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					// LogUtil.getLogger().error("关闭输出流时发生异常,堆栈信息如下", e);
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					// LogUtil.getLogger().error("关闭输入流时发生异常,堆栈信息如下", e);
					e.printStackTrace();
				}
			}
			if (httpURLConnection != null) {
				httpURLConnection.disconnect();
				httpURLConnection = null;
			}
		}
	}

	/**
	 * 发送HTTP_GET请求获取InputStream
	 * 
	 * @see 该方法会自动关闭连接,释放资源
	 * @param requestURL
	 *            请求地址(含参数)
	 * @return InputStream
	 */
	public static InputStream sendGetRequest(String reqURL) {
		// long responseLength = 0; // 响应长度
		InputStream responseContent = null; // 响应内容
		CloseableHttpClient httpclient = HttpClients.createDefault();  // 创建默认的httpClient实例
		HttpGet httpGet = new HttpGet(reqURL); // 创建org.apache.http.client.methods.HttpGet
		try {
			CloseableHttpResponse response = httpclient.execute(httpGet); // 执行GET请求
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				// responseLength = entity.getContentLength();
				responseContent = entity.getContent();
			}
			// System.out.println("请求地址: " + httpGet.getURI());
			// System.out.println("响应状态: " + response.getStatusLine());
			// System.out.println("响应长度: " + responseLength);
			// System.out.println("响应内容: " + responseContent);
		} catch (ClientProtocolException e) {
			// LogUtil.getLogger().error("该异常通常是协议错误导致,比如构造HttpGet对象时传入的协议不对(将'http'写成'htp')或者服务器端返回的内容不符合HTTP协议要求等,堆栈信息如下",
			// e);
			e.printStackTrace();
		} catch (ParseException e) {
			// LogUtil.getLogger().error(e.getMessage(), e);
			e.printStackTrace();
		} catch (IOException e) {
			// LogUtil.getLogger().error("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下",
			// e);
			e.printStackTrace();
		} finally {
			 if (httpclient != null) {  
	                try {  
	                    httpclient.close();  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            } 
		}
		return responseContent;
	}
	
	/**
	 * 以post方式发送json格式请求
	 * @param reqURL 请求地址
	 * @param data 请求数据
	 * @return
	 * @throws Exception
	 */
	public static String postJsonRequest(String reqURL, Object data) throws Exception {
		
		final int TIME_OUT = 5000; // 默认超时时间为5s
		CloseableHttpResponse response = null;
		String responseContent = null;
		CloseableHttpClient httpclient = HttpClients.createDefault(); 

		HttpPost httpPost = new HttpPost(reqURL);
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(TIME_OUT)
				.setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).build();
		httpPost.setConfig(config);
		httpPost.addHeader("Content-type","application/json; charset=utf-8");  
		httpPost.setHeader("Accept", "application/json");    
        
		try {
			String parameters = JSON.toJSONStringWithDateFormat(data, "yyyy-MM-dd HH:mm:ss");
			logger.info(" ********** post to " + reqURL + "   data:" + parameters);
			httpPost.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));

			response = httpclient.execute(httpPost); 
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity,"UTF-8");
				EntityUtils.consume(entity);
			}
			logger.info(" ************* post to " + reqURL + "   successs. return value: " + responseContent);
		} catch (Exception e) {
			logger.error(" ************* post to " + reqURL + "   fail", e);
			throw  e;
		} finally {
			// 关闭连接,释放资源    
            if(httpclient != null){
            	IOUtils.closeQuietly(httpclient);
            }
            if(response != null){
            	IOUtils.closeQuietly(response);
            }
		}
		return responseContent;
	}
	
	public static void main(String[] args) {
//		 String appid = "wx3ade954298518055";
//	     String secret = "c2766782ae5a3e251f9f4cab42f3521b";
//	     String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code=UTF-8&grant_type=authorization_code";
//	     //第一次请求 获取access_token 和 openid
//	     String  oppid = sendGetRequest(requestUrl,"UTF-8");
//	     JSONObject oppidObj =JSONObject.fromObject(oppid);
//	     String access_token = (String) oppidObj.get("access_token");
//	     String openid = (String) oppidObj.get("openid");
//	     System.out.println("*********"+access_token+"******");
//	     System.out.println("*********"+openid+"******");
		
		 Map<String, Object> data = new HashMap<String, Object>();
		 data.put("couponCode", "fjjj99999999999");
		 try{
			String result = HttpClientUtil.postJsonRequest("http://localhost:8080/breakfastApi/op/deleteCoupon", data);
			System.out.println("+++++++++++++++++ " + result);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
	}

}
