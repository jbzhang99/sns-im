package com.inga.server.sdk.session;


import com.inga.constant.CIMConstant;
import com.inga.server.sdk.util.SNSJedisUtil;

import java.util.List;


/**
 * session管理
 *  使用redis存储用户的session对象,具体的channel保存在netty中,分布式的时候,
 *  使用account查找用户具体在那一台机器上面,然后具体在那一套机器上面进行具体的消息推送
 *
 * Created by abing on 2017/5/19.
 */
public class JedisSessionManager implements SessionManager {

    private SNSJedisUtil redisUtil;

	private static JedisSessionManager instance = null;
	
	private JedisSessionManager() {
		redisUtil = SNSJedisUtil.getInstance(); 
	}
	
	public static JedisSessionManager getInstance() {
		if(instance == null) {
			instance = new JedisSessionManager();
		}
		return instance;
	}

    @Override
    public void add(CIMSession session) {

        redisUtil.set(CIMConstant.PRE + session.getAccount() , session);
    }

    @Override
    public void update(CIMSession session) {
        redisUtil.set(CIMConstant.PRE + session.getAccount() , session);
    }

    @Override
    public CIMSession get(String account) {
        CIMSession session = (CIMSession) redisUtil.get(CIMConstant.PRE + account);
        return session;
    }

    @Override
    public List<CIMSession> queryAll() {
        return null;
    }

    public void remove(String account) {
    	if(account == null || "".equals(account)) {
    		return;
    	}
        redisUtil.del(CIMConstant.PRE + account);
    }

    @Override
    public boolean exists(String account) {

        return redisUtil.exists(CIMConstant.PRE + account);
    }
}
