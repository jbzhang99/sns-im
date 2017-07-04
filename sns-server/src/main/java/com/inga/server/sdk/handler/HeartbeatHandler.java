package com.inga.server.sdk.handler;


import com.inga.server.sdk.mdel.ReplyBody;
import com.inga.server.sdk.mdel.SentBody;
import com.inga.server.sdk.session.CIMSession;

/**
 *记录心跳实现
 * 
 */
public class HeartbeatHandler implements CIMRequestHandler {

	public ReplyBody process(CIMSession session, SentBody message) {
		//收到心跳响应，设置心跳时间
		session.setHeartbeat(System.currentTimeMillis());
		return null;
	}
	
 
	
}
