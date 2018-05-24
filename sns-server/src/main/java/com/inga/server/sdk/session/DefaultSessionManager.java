
package com.inga.server.sdk.session;


import com.inga.constant.CIMConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/** 
 * 自带默认 session管理实现， 各位可以自行实现 AbstractSessionManager接口来实现自己的 session管理
 * 服务器集群时 须要将CIMSession 信息存入数据库或者nosql 等 第三方存储空间中，便于所有服务器都可以访问
 */
public class DefaultSessionManager implements SessionManager{


    private static HashMap<String,CIMSession> sessions =new  HashMap<String,CIMSession>();
    private static final AtomicInteger connectionsCounter = new AtomicInteger(0);


    public void add(CIMSession session) {
        if(session!=null){
        	session.setAttribute(CIMConstant.SESSION_KEY, session.getAccount());
        	sessions.put(session.getAccount(), session);
        	connectionsCounter.incrementAndGet();
        }
    }

     
    public CIMSession get(String account) {
       return sessions.get(account);
    }


    public List<CIMSession> queryAll() {
    	List<CIMSession> list = new ArrayList<CIMSession>();
    	list.addAll(sessions.values());
        return list;
    }
 
    public void  remove(CIMSession session) {
    	sessions.remove(session.getAttribute(CIMConstant.SESSION_KEY));
    }

     
    public void  remove(String account) {
    	sessions.remove(account);
    }

    @Override
    public boolean exists(String account) {
        return sessions.containsKey(account);
    }


    public boolean containsCIMSession(String account)
    {
    	return sessions.containsKey(account);
    }


	@Override
	public void update(CIMSession session) {
		sessions.put(session.getAccount(), session);
	}
 
}
