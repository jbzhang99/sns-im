package com.inga.server.sdk.handler;

import com.inga.constant.CIMConstant;
import com.inga.server.sdk.filter.ServerMessageDecoder;
import com.inga.server.sdk.filter.ServerMessageEncoder;
import com.inga.model.HeartbeatRequest;
import com.inga.model.ReplyBody;
import com.inga.model.SentBody;
import com.inga.server.sdk.session.CIMSession;
import com.inga.server.sdk.session.DefaultSessionManager;
import com.inga.server.sdk.session.SessionManager;
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
 * Date  2018/1/15
 * Time  上午10:18
 */
@Sharable
public class CIMNioSocketAcceptor extends SimpleChannelInboundHandler<SentBody> {
	
	private final static String CIMSESSION_CLOSED_HANDLER_KEY = "client_cimsession_closed";
	/**
	 * 绑定请求类型信息
	 */
	public static Map<String, CIMRequestHandler> handlers = new HashMap<String, CIMRequestHandler>();

	private int port;

	private SessionManager sessionManager = new DefaultSessionManager();

	/**
	 * 保存channel的缓存信息
	 */
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


  	public static final int PING_TIME_OUT = 30;//心跳响应 超时为30秒

	/**
	 * 初始化
	 * @throws IOException
	 */
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
                	 //心跳探测
//                	 ch.pipeline().addLast(new IdleStateHandler(5,5,5));
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
		CIMSession cimSession =new  CIMSession(ctx.channel());
		sessionManager.remove(cimSession.getAccount());
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

	/**
	 * netty连接的建立
	 * @param ctx
	 */
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) {
		System.out.println("sessionCreated()... from "+ctx.channel().remoteAddress()+" nid:" + ctx.channel().id().asShortText());
	}

	/**
	 * 读取客户段发送过来的消息
	 *
	 * @param ctx
	 * @param body
	 * @throws Exception
     */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, SentBody body) throws Exception {
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
	 * netty连接的删除
	 * @param ctx
	 * @throws Exception
     */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		CIMSession cimSession =new  CIMSession(ctx.channel());
		System.out.println("sessionClosed()... from "+ctx.channel().remoteAddress()+" nid:"+cimSession.getNid() +",isConnected:"+ctx.channel().isActive());
		CIMRequestHandler handler = handlers.get(CIMSESSION_CLOSED_HANDLER_KEY);
		if(handler!=null){
			handler.process(cimSession, null);
		}else {
			System.out.println("删除cimsession等");
			sessionManager.remove(cimSession.getAccount());
		}

	}

	/**
	 *
	 * 心跳探测使用
	 *
	 * @param ctx
	 * @param evt
	 * @throws Exception
     */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent && ((IdleStateEvent) evt).state().equals(IdleState.WRITER_IDLE)) {
			ctx.channel().attr(AttributeKey.valueOf(CIMConstant.HEARTBEAT_KEY)).set(System.currentTimeMillis());
			ctx.channel().writeAndFlush(HeartbeatRequest.getInstance());
			System.out.println(IdleState.WRITER_IDLE +"... from "+ctx.channel().remoteAddress()+" nid:" +ctx.channel().id().asShortText());
	    }

	    //如果心跳请求发出30秒内没收到响应，则关闭连接
	    if (evt instanceof IdleStateEvent && ((IdleStateEvent) evt).state().equals(IdleState.READER_IDLE)){

			System.out.println(IdleState.READER_IDLE +"... from "+ctx.channel().remoteAddress()+" nid:" +ctx.channel().id().asShortText());
	    	Long lastTime = (Long) ctx.channel().attr(AttributeKey.valueOf(CIMConstant.HEARTBEAT_KEY)).get();
	     	if(lastTime != null && System.currentTimeMillis() - lastTime >= PING_TIME_OUT){
	     		ctx.channel().close();
				channels.remove(ctx.channel());
	     	}
	     	ctx.channel().attr(AttributeKey.valueOf(CIMConstant.HEARTBEAT_KEY)).set(null);
	    }

	}

	/**
	 *  不进行异常的抛出,一处channel对象
	 *	因为使用了分布式的调用，客户端主动去建立socket连接，主动去断开socket连接.(防止一台机器建立过多的tcp连接，防止达到系统上限)
	 * 	//TODO 	客户端主动断开连接的时候会到这里面运行	(特殊的把错误抛出进行了日志的打印，后续测试继续观察)
	 *
	 * @param ctx
	 * @param cause
     */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		System.out.println(" 客户端链接断开 "+ctx.channel().remoteAddress()+" isConnected:"+ctx.channel().isActive()+" nid:" +ctx.channel().id().asShortText());
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
