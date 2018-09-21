package com.inga.server.sdk;


import com.inga.constant.CIMConstant;
import com.inga.server.sdk.handler.CIMNioSocketAcceptor;
import com.inga.server.sdk.handler.CIMRequestHandler;
import com.inga.server.sdk.handler.request.BindHandler;
import com.inga.server.sdk.handler.request.HeartBeatHandler;
import com.inga.server.sdk.handler.request.PullMessageHandler;

import java.io.IOException;
import java.util.HashMap;


/**
 *
 * Date  2018/1/15
 * Time  上午10:20
 */
public class Server {

    public static void main(String[] args) throws IOException {
        CIMNioSocketAcceptor acceptor = new CIMNioSocketAcceptor();
        acceptor.setPort(23450);

        HashMap<String , CIMRequestHandler> map = new HashMap<String, CIMRequestHandler>();

        map.put(CIMConstant.MESSAGE_TYPE.CLIENT_BIND , new BindHandler());
        map.put(CIMConstant.MESSAGE_TYPE.PULL_MSG , new PullMessageHandler());
        map.put(CIMConstant.MESSAGE_TYPE.HEART_BEAT , new HeartBeatHandler());

        acceptor.setHandlers(map);

        acceptor.bind();

    }
}
