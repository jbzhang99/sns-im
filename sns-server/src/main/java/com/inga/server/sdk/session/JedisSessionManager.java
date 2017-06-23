package com.inga.server.sdk.session;


import com.inga.server.sdk.constant.CIMConstant;
import com.inga.server.sdk.util.SNSJedisUtil;

import java.util.List;


/**
 * session管理
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
