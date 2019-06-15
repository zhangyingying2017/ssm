package cn.edu.zzuli.common.pushmsg;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;

/**
 * 消息推送
 * @author zsp
 *
 */
public class pushMsgTool {

	private final static Logger logger = Logger.getLogger(pushMsgTool.class);
	/**
	 * 向单个对象推送消息
	 * @param pushMsg
	 * @return PushMsgToSingleDeviceResponse
	 */
	public static Boolean PushMsgToSingleDevice(PushMsg pushMsg){
		
		
		PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest();
		String apiKey;
		String secretKey;
		if(pushMsg.getSysType() == 1){
			 apiKey = "*******";
	         secretKey = "*******";
		}else{
		     apiKey = "********";
	         secretKey = "********";
		}
	    PushKeyPair pair = new PushKeyPair(apiKey,secretKey);
	    BaiduPushClient pushClient = new BaiduPushClient(pair,BaiduPushConstants.CHANNEL_REST_URL);
	    try {
	    	Map<String,Object> map = new HashMap<String,Object>();
	        // 设置请求参数
	    	request.addMessageType(1);
	    	request.addChannelId(pushMsg.getClientId());
	    	if(pushMsg.getSysType() == 1){
	    		request.addDeviceType(3);
	    		map.put("title", pushMsg.getTitle());
	 	        map.put("url", pushMsg.getUrl());
	 	        map.put("description", pushMsg.getDescription());
	    	}else{
	    		request.addDeviceType(4);
	    		Map<String,String> mapAps = new HashMap<String,String>();
	    		mapAps.put("alert", pushMsg.getTitle());
	    		mapAps.put("sound", "default");
	    		mapAps.put("badge", "1");
	    		map.put("aps", mapAps);
	    	}
	    	request.addMessage(JSON.toJSONString(map));
	        // 请求推送
	        PushMsgToSingleDeviceResponse response = pushClient.pushMsgToSingleDevice(request);
        }catch(Exception e){
        	logger.error("推送失败： " ,e);
        }
		
		return true;
	}
}
