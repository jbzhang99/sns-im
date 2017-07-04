package com.inga.server.sdk.handler;

import com.inga.server.sdk.mdel.ReplyBody;
import com.inga.server.sdk.mdel.SentBody;
import com.inga.server.sdk.session.CIMSession;

/**
 *  请求处理接口,所有的请求实现必须实现此接口
 */


public interface  CIMRequestHandler  {

   ReplyBody process(CIMSession session, SentBody message);
}
