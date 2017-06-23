package com.inga.server.sdk.handler;

import com.inga.server.sdk.constant.CIMConstant;
import com.inga.server.sdk.filter.ServerMessageDecoder;
import com.inga.server.sdk.filter.ServerMessageEncoder;
import com.inga.server.sdk.mdel.HeartbeatRequest;
import com.inga.server.sdk.mdel.ReplyBody;
import com.inga.server.sdk.mdel.SentBody;
import com.inga.server.sdk.session.CIMSession;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * netty 信息请求的主方法
 *
 * Created by abing on 2017/5/26.
 */
@Sharable
public class CIMNioSocketAcceptor extends SimpleChannelInboundHandler<SentBody> {
	
	private final static String CIMSESSION_CLOSED_HANDLER_KEY = "client_cimsession_closed";
	private Logger logger = LoggerFactory.getLogger(CIMNioSocketAcceptor.class);
	//绑定请求类型信息
	public static Map<String, CIMRequestHandler> handlers = new HashMap<String, CIMRequestHandler>();
	private int port;

	//保存channel的缓存信息
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //连接空闲时间
  	public static final int READ_IDLE_TIME = 120;//秒
  	
  	//连接空闲时间
  	public static final int WRITE_IDLE_TIME = 120;//秒
  	
  	public static final int PING_TIME_OUT = 30;//心跳响应 超时为30秒
  	
    public void bind() throws IOException{
    	ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup());
		bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
		//设置的是未链接队列 和 已连接队列 总和的最大值
		bootstrap.childOption(ChannelOption.SO_BACKLOG , 1000000);
		bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                	 ch.pipeline().addLast(new ServerMessageDecoder());
                	 ch.pipeline().addLast(new ServerMessageEncoder());
                	 ch.pipeline().addLast(new IdleStateHandler(60,40,35));
                	 ch.pipeline().addLast(CIMNioSocketAcceptor.this);
                 }
       });
       bootstrap.bind(port);
    }


	/**
	 * 连接建立的时候去ChannelGroup中添加
	 * @param ctx
	 * @throws Exception
     */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		channels.add(ctx.channel());
	}

	/**
	 * 断开连接的时候去ChannelGroup中删除
	 * @param ctx
	 * @throws Exception
     */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		channels.remove(ctx.channel());
	}

	public void channelRegistered(ChannelHandlerContext ctx) {
		logger.info("sessionCreated()... from "+ctx.channel().remoteAddress()+" nid:" + ctx.channel().id().asShortText());
	}

	/**
	 * 读取客户段发送过来的消息
	 * @param ctx
	 * @param body
	 * @throws Exception
     */
	protected void channelRead0(ChannelHandlerContext ctx, SentBody body) throws Exception {
		System.out.println("channelRead0 this is ===");
		Channel ch = ctx.channel();
		CIMSession cimSession =new  CIMSession(ch);
		CIMRequestHandler handler = handlers.get(body.getKey());
		if (handler == null) {
			ReplyBody reply = new ReplyBody();
			reply.setKey(body.getKey());
			reply.setCode(CIMConstant.ReturnCode.CODE_404);
			reply.setMessage("KEY:"+body.getKey()+"  not defined on server");
			cimSession.write(reply);
		} else {
			ReplyBody reply = handler.process(cimSession, body);
			if(reply!=null){
				reply.setKey(body.getKey());
				cimSession.write(reply);
	        }
		}
	}


	/**
	 *
	 * @param ctx
	 * @throws Exception
     */
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		CIMSession cimSession =new  CIMSession(ctx.channel());
		logger.warn("sessionClosed()... from "+ctx.channel().remoteAddress()+" nid:"+cimSession.getNid() +",isConnected:"+ctx.channel().isActive());
		CIMRequestHandler handler = handlers.get(CIMSESSION_CLOSED_HANDLER_KEY);
		if(handler!=null){
			handler.process(cimSession, null);
		}

	}

	/**
	 * 心跳探测使用
	 * 	//TODO 还没有进行测试调用，后续开发
	 * @param ctx
	 * @param evt
	 * @throws Exception
     */
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent && ((IdleStateEvent) evt).state().equals(IdleState.WRITER_IDLE)) {
			ctx.channel().attr(AttributeKey.valueOf(CIMConstant.HEARTBEAT_KEY)).set(System.currentTimeMillis());
			ctx.channel().writeAndFlush(HeartbeatRequest.getInstance());
			logger.debug(IdleState.WRITER_IDLE +"... from "+ctx.channel().remoteAddress()+" nid:" +ctx.channel().id().asShortText());
	    }

	    //如果心跳请求发出30秒内没收到响应，则关闭连接
	    if (evt instanceof IdleStateEvent && ((IdleStateEvent) evt).state().equals(IdleState.READER_IDLE)){
	    	
			logger.debug(IdleState.READER_IDLE +"... from "+ctx.channel().remoteAddress()+" nid:" +ctx.channel().id().asShortText());
	    	Long lastTime = (Long) ctx.channel().attr(AttributeKey.valueOf(CIMConstant.HEARTBEAT_KEY)).get();
	     	if(lastTime != null && System.currentTimeMillis() - lastTime >= PING_TIME_OUT){
	     		ctx.channel().close();
				channels.remove(ctx.channel());
	     	}
	     	ctx.channel().attr(AttributeKey.valueOf(CIMConstant.HEARTBEAT_KEY)).set(null);
	    }

	}

	/**
	 * 异常报错
	 *	因为使用了分布式的调用，客户端主动去建立socket连接，主动去断开socket连接.(防止一台机器建立过多的tcp连接，防止达到系统上限)
	 * 	//TODO 	客户端主动断开连接的时候会到这里面运行	(特殊的把错误抛出进行了日志的打印，后续测试继续观察)
	 *
	 * @param ctx
	 * @param cause
     */
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		logger.info(" 客户端链接断开 "+ctx.channel().remoteAddress()+" isConnected:"+ctx.channel().isActive()+" nid:" +ctx.channel().id().asShortText());
		ctx.channel().close();
		channels.remove(ctx.channel());

	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setHandlers(HashMap<String, CIMRequestHandler> handlers) {
		this.handlers = handlers;
	}
	
}
