package com.inga.server.sdk.handler.request;


import com.inga.constant.CIMConstant;
import com.inga.server.sdk.handler.CIMNioSocketAcceptor;
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
 * Time  上午10:19
 * Author bingbing.wang@corp.elong.com
 */
public class PullMessageHandler implements CIMRequestHandler {

    private SessionManager sessionManager = new DefaultSessionManager();

    @Override
    public ReplyBody process(CIMSession session, SentBody message) {

        ReplyBody replyBody = new ReplyBody();
        replyBody.setKey(message.getKey());
        replyBody.setCode(CIMConstant.ReturnCode.CODE_200);
        replyBody.setTimestamp(System.currentTimeMillis());

        try{
            if (message.containsKey(CIMConstant.RECEIVER)) {
                ReplyBody pullBody = new ReplyBody();
                pullBody.setKey(CIMConstant.MESSAGE_TYPE.PULL_MSG);
                pullBody.setCode(CIMConstant.ReturnCode.CODE_200);
                pullBody.setTimestamp(System.currentTimeMillis());
                pullBody.setMessage(CIMConstant.OK);

                if (message.containsKey(CIMConstant.TITLE)) {
                    pullBody.put(CIMConstant.TITLE , message.get(CIMConstant.TITLE));
                }

                if (message.containsKey(CIMConstant.CONTENT)) {
                    pullBody.put(CIMConstant.CONTENT , message.get(CIMConstant.CONTENT));
                }

                String receiveID = message.get(CIMConstant.RECEIVER);
                //需要推送到的那个人的account
                CIMSession cimSession = sessionManager.get(receiveID);
                //主要是为了redis中不能保存channel对象 而做的
                if (cimSession != null) {
                    cimSession.setChannel(CIMNioSocketAcceptor.channels.find(cimSession.getChannelId()));
                }
                cimSession.write(pullBody);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }


        return replyBody;
    }
}
