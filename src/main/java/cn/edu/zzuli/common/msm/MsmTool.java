package cn.edu.zzuli.common.msm;

import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;

import com.cloopen.rest.sdk.CCPRestSmsSDK;

import cn.edu.zzuli.common.utils.StringUtil;

/**
 * 短信
 * @author zhangjinfeng
 *
 */
public class MsmTool {
	
	
	private final static Logger log = Logger.getLogger(MsmTool.class);
	/*private static String domain  = "dysmsapi.aliyuncs.com";
	private static String product = "Dysmsapi";
	private static String signName = "智慧校车";
	
	private static String accessKeyId = "*******";
	private static String accessKeySecret = "***************"; */
	
	private static CCPRestSmsSDK cCPRestSmsSDK = null ;
	
	private static String accountSid = "******************";
	
	private static String authToken = "******************";
	/*生产环境*/
	private static String restUri = "app.cloopen.com";
	
	private static String port = "8883"; 
	/*运营环境 快乐早点*/
	private static String appId = "8aaf07085ea24877015ed16949a40faa";
	
	private static String appToken = "00fc12c1d977216d521d7b895bd84c98";
	
	
	public static enum msmType {password, loginAndRegister};//password 找回密码；registe注册验证

	/**
	 * 平台 云通讯
	 * 发送短息
	 * @param mobile 手机号
	 * @param msm 发送的内容
	 * @param type 类型 password 找回密码；registe注册验证
	 * @return 0：发送失败；1:成功；2、手机号码错误；3:内容不能为空
	 * @author zhangjinfeng
	 */
	public static int sendMsm(String mobile, String msm, MsmTool.msmType type){
		
	    String templete = null ;
		HashMap<String, Object> result = null;
		int f = 0;
		if(StringUtil.isPhone(true, mobile)){
			if( !StringUtil.isEmpty(msm) ){
				try {
					
					if(type == MsmTool.msmType.password){//密码找回
					
					 }else if(type == MsmTool.msmType.loginAndRegister){//注册
						 
						 templete =  "208889";
					 }
					//初始化SDK
					CCPRestSmsSDK restAPI = MsmTool.getCCPRestSmsSDK();
					
					//******************************注释*********************************************
					//*初始化服务器地址和端口                                                       *
					//*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
					//*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
					//*******************************************************************************
					restAPI.init(restUri, port);
					
					//******************************注释*********************************************
					//*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
					//*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
					//*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
					//*******************************************************************************
					restAPI.setAccount(accountSid, authToken);
					
					
					//******************************注释*********************************************
					//*初始化应用ID                                                                 *
					//*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
					//*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
					//*******************************************************************************
					restAPI.setAppId(appId);
					
					
					//******************************注释****************************************************************
					//*调用发送模板短信的接口发送短信                                                                  *
					//*参数顺序说明：                                                                                  *
					//*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号                          *
					//*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。    *
					//*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
					//*第三个参数是要替换的内容数组。																														       *
					//**************************************************************************************************
					
					//**************************************举例说明***********************************************************************
					//*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
					//*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});																		  *
					//*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
					//*********************************************************************************************************************
					result = restAPI.sendTemplateSMS(mobile,templete,new String[]{msm,"60秒"});
					
					if("000000".equals(result.get("statusCode"))){
						//正常返回输出data包体信息（map）
						HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
						log.info("短信接口返回的信息 ： " + result );
						Set<String> keySet = data.keySet();
						for(String key:keySet){
							Object object = data.get(key);
							System.out.println(key +" = "+object);
						}
					}else{
						//异常返回输出错误码和错误信息
						log.info("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
					}

				} catch (Exception e) {
					log.error("调用短信接口出错：" +result, e);
					f = 0;
				}
				
			}else{//内容为空
				f = 3;
			}
		}else{//手机号码校验失败
			f = 2 ;
		}
		return f;
		
	}
	
	/**
	 * 
	 * 发送短息
	 * @param mobile 手机号
	 * @param msm 发送的内容
	 * @param type 类型 password 找回密码；registe注册验证
	 * @return 0：发送失败；1:成功；2、手机号码错误；3:内容不能为空
	 */
	/*public static int sendMsm(String mobile, String msm, MsmTool.msmType type){
		int f = 0;
		if(StringUtil.isPhone(true, mobile)){
			if( !StringUtil.isEmpty(msm) ){
				try {
					String tempCode = "";
					String templateParam = "";
					if(type == MsmTool.msmType.password){//密码找回
						tempCode = "SMS_89890425";
						templateParam = "{\"code\":\""+msm+"\"}";
					 }else if(type == MsmTool.msmType.loginAndRegister){//注册
						 tempCode = "SMS_89890426";
						 templateParam = "{\"code\":\""+msm+"\"}";
					 }
					//初始化ascClient,暂时不支持多region
					IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
					DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
					IAcsClient acsClient = new DefaultAcsClient(profile);
					
					//POST请求
					 SendSmsRequest request = new SendSmsRequest();
					 request.setMethod(MethodType.POST);
					 request.setPhoneNumbers(mobile);
					 request.setSignName(signName);//签名
					 request.setTemplateCode(tempCode);//模板编码
					 request.setTemplateParam(templateParam);//模板参数
					 
					 SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
					 if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
						 //请求成功
						 f = 1;
					 }
				} catch (Exception e) {
					f = 0;
				}
				
			}else{//内容为空
				f = 3;
			}
		}else{//手机号码校验失败
			f = 2 ;
		}
		return f;
	}*/
	
	public static CCPRestSmsSDK getCCPRestSmsSDK(){
		
		if(cCPRestSmsSDK ==null){
			
			synchronized(MsmTool.class){
				
				if(cCPRestSmsSDK ==null){
					
					cCPRestSmsSDK = new CCPRestSmsSDK();
				}
			}
		}
		
		return cCPRestSmsSDK;
	}
}
