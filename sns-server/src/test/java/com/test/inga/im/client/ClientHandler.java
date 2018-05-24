package com.test.inga.im.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.util.StringUtils;

/**
 *
 * Date  2018/1/15
 * Time  上午10:15
 * Author bingbing.wang@corp.elong.com
 */
public class ClientHandler extends SimpleChannelInboundHandler<Object> {

    public static Channel channel;

    public void init(String host , int port){
        if (StringUtils.isEmpty(host)){
            host = "127.0.0.1";
        }
        if ( port == 0){
            port = 23450;
        }

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.option(ChannelOption.SO_KEEPALIVE , true);
        bootstrap.option(ChannelOption.TCP_NODELAY , true);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(ClientHandler.this);
            }
        });

        try {
            channel = bootstrap.connect(host , port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead0 : " + msg.toString());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead : " + msg.toString());
    }
}
