package com.test.inga.im.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by abing on 2017/7/4.
 */
public class ServerHandler extends SimpleChannelInboundHandler<Object> {

    public void init(int port){
        if (port == 0){
            port = 23450;
        }
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(new NioEventLoopGroup() , new NioEventLoopGroup());
        bootstrap.option(ChannelOption.SO_KEEPALIVE , true);
        bootstrap.option(ChannelOption.TCP_NODELAY , true);
        bootstrap.option(ChannelOption.SO_TIMEOUT , 3000);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new StringEncoder());
                ch.pipeline().addLast(ServerHandler.this);
            }
        });
        bootstrap.bind(port);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(" channelRead0 : " + msg.toString());
        ctx.writeAndFlush(msg);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead : "  + msg.toString());
        ctx.channel().writeAndFlush(msg);
    }
}
