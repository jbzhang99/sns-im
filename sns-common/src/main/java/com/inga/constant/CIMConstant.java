
package com.inga.constant;


/**
 * 常量
 */
public   interface  CIMConstant  {
    
	public static interface ReturnCode{
		
		String CODE_200 ="200";

		String CODE_404 ="404";
		
		String CODE_403 ="403";
		
		String CODE_500 ="500";
		
	}

	String PRE = "sns_";
	String MID = "mid";
	String ACTION = "action";
	String DEVICE_ID = "deviceId";
	String HOST = "host";
	String ACCOUNT = "account";
	String CHANNEL = "channel";
	String DEVICE_MODEL = "deviceModel";
	String CLIENT_VERSION = "clientVersion";
	String SYSTEM_VERSION = "systemVersion";
	String PACKAGE_NAME = "packageName";
	String BIND_TIME = "bindTime";
	String HEART_BEAT = "heartbeat";
	String LONGITUDE = "longitude";
	String LATITUDE = "latitude";
	String LOCATION = "location";
	String APNS_ABLE = "apnsAble";
	String STATUS = "status";

	String VERSION = "version";
	String RECEIVER = "receiver";
	String SENDER = "sender";
	String FORMAT = "format";
	String EXTRA = "extra";

	String TITLE = "title";
	String CONTENT = "content";

	String OK = "ok";

	
	String SESSION_KEY ="account";
	String HEARTBEAT_KEY ="heartbeat";
	
	String CLIENT_HEARTBEAT ="client_heartbeat";
	
	//消息头长度为3个字节，第一个字节为消息类型，第二，第三字节 转换int后为消息长度
	int DATA_HEADER_LENGTH = 3;
	
	public static interface ProtobufType{
		byte C_H_RS = 0;
		byte S_H_RQ = 1;
		byte MESSAGE = 2;
		byte SENTBODY = 3;
	    byte REPLYBODY = 4;
	}


	public static interface MESSAGE_TYPE {
		//客户端绑定到服务端
		String CLIENT_BIND = "client_bind";
		//客户端之间消息推送
		String PULL_MSG = "pull_msg";
		//客户端与服务端之间心跳探测
		String HEART_BEAT = "heart_beat";
	}


	public static interface MessageAction{
		
	    //被其他设备登录挤下线消息
		String ACTION_999 ="999";
		//被系统禁用消息
		String ACTION_444 ="444";
    }

	public static interface RedisKey{

		String IM_SERVER_INFO = "imServerInfo";

	}
   
}
