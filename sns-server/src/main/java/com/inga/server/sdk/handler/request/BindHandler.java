package com.inga.server.sdk.handler.request;


import com.inga.constant.CIMConstant;
import com.inga.server.sdk.handler.CIMRequestHandler;
import com.inga.model.ReplyBody;
import com.inga.model.SentBody;
import com.inga.server.sdk.session.CIMSession;
import com.inga.server.sdk.session.DefaultSessionManager;
import com.inga.server.sdk.session.JedisSessionManager;
import com.inga.server.sdk.session.SessionManager;

/**
 *
 * Date  2018/1/15
 * Time  上午10:17
 */
public class BindHandler implements CIMRequestHandler {

    private SessionManager sessionManager = new DefaultSessionManager();
    @Override
    public ReplyBody process(CIMSession session, SentBody message) {
        ReplyBody replyBody = new ReplyBody();
        replyBody.setKey(message.getKey());
        replyBody.setCode(CIMConstant.ReturnCode.CODE_200);
        replyBody.setTimestamp(System.currentTimeMillis());

        try {
            if (message.containsKey(CIMConstant.ACCOUNT)) {
                session.setAccount(message.get(CIMConstant.ACCOUNT));
            }
            if (message.containsKey(CIMConstant.CHANNEL)) {
                session.setChannel(message.get(CIMConstant.CHANNEL));
            }
            if (message.containsKey(CIMConstant.DEVICE_ID)) {
                session.setDeviceId(message.get(CIMConstant.DEVICE_ID));
            }
            if (message.containsKey(CIMConstant.CLIENT_VERSION)) {
                session.setClientVersion(message.get(CIMConstant.CLIENT_VERSION));
            }
            if (message.containsKey(CIMConstant.DEVICE_MODEL)) {
                session.setDeviceModel(message.get(CIMConstant.DEVICE_MODEL));
            }
            if (message.containsKey(CIMConstant.SYSTEM_VERSION)) {
                session.setSystemVersion(message.get(CIMConstant.SYSTEM_VERSION));
            }
            //保存客户端session到内存中
            sessionManager.add(session);
        } catch (Exception e) {
            e.printStackTrace();
            replyBody.setCode(CIMConstant.ReturnCode.CODE_500);
            replyBody.setTimestamp(System.currentTimeMillis());
            replyBody.setMessage("客户端绑定到服务端绑定失败 : " + e.getMessage() );
        }
        return replyBody;
    }
}
