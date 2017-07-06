package com.inga.server.sdk.handler.request;


import com.inga.constant.CIMConstant;
import com.inga.server.sdk.handler.CIMRequestHandler;
import com.inga.model.HeartbeatResponse;
import com.inga.model.ReplyBody;
import com.inga.model.SentBody;
import com.inga.server.sdk.session.CIMSession;

/**
 * Created by abing on 2017/5/31.
 */
public class HeartBeatHandler implements CIMRequestHandler {


    @Override
    public ReplyBody process(CIMSession session, SentBody message) {
        ReplyBody replyBody = new ReplyBody();
        replyBody.setKey(message.getKey());
        replyBody.setCode(CIMConstant.ReturnCode.CODE_200);
        replyBody.setTimestamp(System.currentTimeMillis());

        session.write(HeartbeatResponse.getInstance());

        return replyBody;
    }
}
