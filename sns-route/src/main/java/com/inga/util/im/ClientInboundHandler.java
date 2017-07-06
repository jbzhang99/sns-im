package com.inga.util.im;

import com.inga.constant.CIMConstant;
import com.inga.model.Message;
import com.inga.model.SentBody;
import com.inga.util.StringUtils;
import com.inga.util.buffer.ClientMessageDecoder;
import com.inga.util.buffer.ClientMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by abing on 2017/7/4.
 */
public class ClientInboundHandler extends SimpleChannelInboundHandler<Object> {

    public static Channel channel;

    private String host;
    private int port;

    public void init(){
        System.out.println("ClientInboundHandler init");
        try {
            if (StringUtils.isEmpty(host)){
                host = "localhost";
            }
            if (port == 0){
                port = 23450;
            }
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(new NioEventLoopGroup());
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE.TCP_NODELAY , true);
            bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS ,30000);
            bootstrap.option(ChannelOption.SO_KEEPALIVE , true);

            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ClientMessageDecoder());
                    ch.pipeline().addLast(new ClientMessageEncoder());
                    ch.pipeline().addLast(new IdleStateHandler(30,20,10));
                    ch.pipeline().addLast(ClientInboundHandler.this);
                }
            });
            channel = bootstrap.connect(host , port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println(evt.toString());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg.toString());
    }


    public void push(Message message){
        SentBody sentBody = new SentBody();
        sentBody.setKey(CIMConstant.MESSAGE_TYPE.PULL_MSG);
        sentBody.setTimestamp(System.currentTimeMillis());
        if (StringUtils.isNotEmpty(message.getTitle())) {
            sentBody.put(CIMConstant.TITLE , message.getTitle());
        }
        if (StringUtils.isNotEmpty(message.getContent())){
            sentBody.put(CIMConstant.CONTENT , message.getContent());
        }
        if (StringUtils.isNotEmpty(message.getReceiver())){
            sentBody.put(CIMConstant.RECEIVER , message.getReceiver());
        }
        if (StringUtils.isNotEmpty(message.getSender())) {
            sentBody.put(CIMConstant.SENDER , message.getSender());
        }

        channel.writeAndFlush(sentBody);

    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
