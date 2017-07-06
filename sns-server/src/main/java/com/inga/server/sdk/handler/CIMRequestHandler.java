package com.inga.server.sdk.handler;

import com.inga.model.ReplyBody;
import com.inga.model.SentBody;
import com.inga.server.sdk.session.CIMSession;

/**
 *  请求处理接口,所有的请求实现必须实现此接口
 */


public interface  CIMRequestHandler  {

   ReplyBody process(CIMSession session, SentBody message);
}
