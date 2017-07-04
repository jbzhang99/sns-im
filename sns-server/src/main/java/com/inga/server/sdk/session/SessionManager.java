
package com.inga.server.sdk.session;

import java.util.List;


/**
 *  客户端的 session管理接口
 *  可自行实现此接口管理session
 */
 
public interface  SessionManager  {

	
	/**
	 * 添加新的session
	 */
	public void add(CIMSession session);
	
	/**
	 * 更新session
	 */
	public void update(CIMSession session);
	
	/**
	 * 
	 * @param account 客户端session的 key 一般可用 用户账号来对应session
	 * @return
	 */
	CIMSession get(String account);
	
	/**
	 * 获取所有session
	 * @return
	 */
	public List<CIMSession> queryAll();
	
    
    /**
	 * 删除session
	 * @param account
	 */
    public void  remove(String account);

	/**
	 * 判断用户是否在线
	 * @param account
	 * @return
     */
	public boolean exists(String account);
    
}
