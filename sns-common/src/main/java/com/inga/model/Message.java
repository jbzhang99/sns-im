
package com.inga.model;

import com.inga.constant.CIMConstant;
import com.inga.model.proto.MessageProto;

import java.io.Serializable;

/**
 * 消息对象
 */
public class Message implements Serializable,Protobufable {

	private static final long serialVersionUID = 1L;

	
	/**
	 * 消息类型，用户自定义消息类别
	 */
	private String mid;
	
	
	/**
	 * 消息类型，用户自定义消息类别
	 */
	private String action;
	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 消息类容，于action 组合为任何类型消息，content 根据 format 可表示为 text,json ,xml数据格式
	 */
	private String content;

	/**
	 * 消息发送者账号
	 */
	private String sender;
	/**
	 * 消息发送者接收者
	 */
	private String receiver;

	/**
	 * content 内容格式
	 */
	private String format;

	/**
	 * 附加内容 内容
	 */
	private String extra;

	/**
	 * im服务器ip
	 */
	private String host;

	/**
	 * im服务器端口
	 */
	private String port;

	private long timestamp;
	
	
	public Message()
	{
		timestamp = System.currentTimeMillis();
	}
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	 

	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	 
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	
	
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}


	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}


	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Message{");
		sb.append("mid='").append(mid).append('\'');
		sb.append(", action='").append(action).append('\'');
		sb.append(", title='").append(title).append('\'');
		sb.append(", content='").append(content).append('\'');
		sb.append(", sender='").append(sender).append('\'');
		sb.append(", receiver='").append(receiver).append('\'');
		sb.append(", format='").append(format).append('\'');
		sb.append(", extra='").append(extra).append('\'');
		sb.append(", host='").append(host).append('\'');
		sb.append(", port='").append(port).append('\'');
		sb.append(", timestamp=").append(timestamp);
		sb.append('}');
		return sb.toString();
	}

	public boolean isNotEmpty(String txt) {
		return txt != null && txt.trim().length()!=0;
	}
//	@Override
	public byte[] getByteArray() {
		MessageProto.Model.Builder builder = MessageProto.Model.newBuilder();
		builder.setMid(mid);
		builder.setAction(action);
		builder.setSender(sender);
		builder.setReceiver(receiver);
		builder.setTimestamp(timestamp);

		/**
		 * 下面字段可能为空
		 */
		if(content!=null){
			builder.setContent(content);	
		}
		if(title!=null){
			builder.setTitle(title);	
		}
		if(extra!=null){
			builder.setExtra(extra);	
		}
		if(format!=null){
			builder.setFormat(format);	
		}
		return builder.build().toByteArray();
	}
//	@Override
	public byte getType() {
		return CIMConstant.ProtobufType.MESSAGE;
	}

	
}
