package com.inga.server.sdk.handler;

import com.inga.constant.CIMConstant;
import com.inga.server.sdk.handler.request.BindHandler;
import com.inga.server.sdk.handler.request.PullMessageHandler;
import com.inga.server.sdk.util.SNSJedisUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * im服务端的启动
 *
 * Created by abing on 2017/7/5.
 */
public class ServerMainHandler {

    private SNSJedisUtil jedisUtil;

    private String host;
    private int port;
    public ServerMainHandler(String host , int port){
        System.out.println("this is ServerMainHandler ");
        this.host = host;
        this.port = port;
        jedisUtil = SNSJedisUtil.getInstance();

    }


    public void init(){
        //init im server
        CIMNioSocketAcceptor acceptor = new CIMNioSocketAcceptor();
        acceptor.setPort(port);
        HashMap<String , CIMRequestHandler> map = new HashMap<String, CIMRequestHandler>();
        map.put(CIMConstant.MESSAGE_TYPE.CLIENT_BIND , new BindHandler());
        map.put(CIMConstant.MESSAGE_TYPE.PULL_MSG , new PullMessageHandler());
        acceptor.setHandlers(map);

        try {
            acceptor.bind();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //add server info to redis
        Object obj = jedisUtil.get(CIMConstant.RedisKey.IM_SERVER_INFO);
        if (obj == null){
            List<String> list = new ArrayList<String>();
            list.add(host);
            jedisUtil.set(CIMConstant.RedisKey.IM_SERVER_INFO , list);
        }else {
            List<String> list = (List<String>) obj;
            list.add(host);
            jedisUtil.set(CIMConstant.RedisKey.IM_SERVER_INFO , list);

        }
    }
}
