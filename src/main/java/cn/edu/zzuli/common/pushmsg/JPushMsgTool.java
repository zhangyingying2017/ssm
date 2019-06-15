package cn.edu.zzuli.common.pushmsg;

import org.apache.log4j.Logger;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;

/**
 * 极光推送工具类
 * 
 * @author zhangjinfeng
 * @date 2017年10月17日下午7:01:40 TODO
 */
public class JPushMsgTool {

	private final static Logger log = Logger.getLogger(JPushMsgTool.class);

	// 在极光注册上传应用的 appKey 和 masterSecret
	private static final String appKey = "*****";

	private static final String masterSecret = "*******";

	private static JPushClient client = null;

	/*
	 * 保存离线的时长。秒为单位。最多支持10天（864000秒）。
	 * 
	 * 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
	 * 
	 * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒
	 */
	private static int timeToLive = 60 * 60 * 24;

	/**
	 * 生成极光推送对象PushPayload
	 * 
	 * @param alias
	 * @param content
	 * @return PushPayload
	 */
	public static PushPayload buildPushPayload(JPushMsg msg) {

		return PushPayload.newBuilder()/***/
				.setAudience(Audience.alias(msg.getAlias()))/** 设置需要推送的别名 **/
				.setPlatform(Platform.all())/** 设置需要推送的平台 **/
				.setMessage(
						Message.newBuilder()/***/
								.setContentType(
										msg.getContentType() == null ? "text" : msg.getContentType())/** 设置请求的文本类型 */
								.setMsgContent(msg.getContent())/** 设置推送的消息主体 **/
								.setTitle(msg.getTitle())/** 推送消息的主体 **/
								.addExtra("data", "")
								/**
								 * 设置需要传到客户端的数据和Map一样的作用
								 * 
								 * extras{
								 * 
								 * username:张三,
								 * 
								 * age:18, }
								 **/
								.build())/****/
				.setOptions(Options.newBuilder().setApnsProduction(false)/****/
						.setTimeToLive(timeToLive)/** 设置存活时间 **/
						.build())
				// true-推送生产环境
				// false-推送开发环境（测试使用参数）//
				// 消息在JPush服务器的失效时间（测试使用参数
				.build();
	}

	/**
	 * 极光推送方法
	 * 
	 * @param alias
	 * @param content
	 * @return PushResult
	 */
	public static PushResult push(JPushMsg msg) {

		jPushClientInstance();
		PushPayload payload = buildPushPayload(msg);
		try {
			return client.sendPush(payload);
		} catch (APIConnectionException e) {
			log.error("连接推送服务器有误. 请稍后再试. ", e);
			return null;
		} catch (APIRequestException e) {
			log.error("推送消息响应出错. . ", e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());
			return null;
		}
	}

	/**
	 * 初始化JPushClient对象
	 */
	private static void jPushClientInstance() {

		if (client == null) {
			synchronized (JPushMsgTool.class) {
				if (client == null) {
					ClientConfig clientConfig = ClientConfig.getInstance();
					client = new JPushClient(masterSecret, appKey, null, clientConfig);
				}
			}
		}
	}
}
