package cn.edu.zzuli.common.pushmsg;
/**
 * 向单个对象推送消息参数
 * @author zsp
 *
 */
public class PushMsg {
	
	private String clientId;//
	
	private Integer sysType;//设备类型0:IOS,1:android
	
	private String title;//标题
	
	private String description;//描述
	
	private String url;//路径

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Integer getSysType() {
		return sysType;
	}

	public void setSysType(Integer sysType) {
		this.sysType = sysType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
