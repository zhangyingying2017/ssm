package cn.edu.zzuli.common.pushmsg;

import java.util.Map;

/**
 * 
 * @author zhangjinfeng
 * @date 2017年10月17日下午10:34:28 TODO
 */
public class JPushMsg {

	/**
	 * 设置唯一别名
	 */
	private String alias;

	/**
	 * 设置消息标题
	 */
	private String title;

	/**
	 * 设置交互数据
	 */
	private Map<String, Object> extra;

	/**
	 * 设置消息内容
	 */
	private String content;

	/**
	 * 设置文本内容
	 */
	private String contentType;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Map<String, Object> getExtra() {
		return extra;
	}

	public void setExtra(Map<String, Object> extra) {
		this.extra = extra;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
