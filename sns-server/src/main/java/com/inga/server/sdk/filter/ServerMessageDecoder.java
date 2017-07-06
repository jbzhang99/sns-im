package com.inga.server.sdk.filter;

import com.inga.constant.CIMConstant;
import com.inga.model.HeartbeatResponse;
import com.inga.model.SentBody;
import com.inga.model.proto.SentBodyProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *  服务端接收消息解码
 */
public class ServerMessageDecoder extends ByteToMessageDecoder {
	
	protected final Logger logger = LoggerFactory.getLogger(ServerMessageDecoder.class);
	@Override
	protected void decode(ChannelHandlerContext arg0, ByteBuf buffer, List<Object> queue) throws Exception {
		/**
		 * 消息头3位
		 */
		if (buffer.readableBytes() < CIMConstant.DATA_HEADER_LENGTH) {
			return;
		}

		buffer.markReaderIndex();

		buffer.markReaderIndex();

		byte conetnType = buffer.readByte();

		byte lv = buffer.readByte();// int 低位
		byte hv = buffer.readByte();// int 高位

		int conetnLength = getContentLength(lv, hv);

		// 如果消息体没有接收完整，则重置读取，等待下一次重新读取
		if (conetnLength > buffer.readableBytes()) {
			buffer.resetReaderIndex();
			return;
		}

		byte[] dataBytes = new byte[conetnLength];
		buffer.readBytes(dataBytes);
	    
	    Object message = mappingMessageObject(dataBytes,conetnType);
	    if(message != null){
	    	queue.add(message);
	    }
	}
	
	public Object mappingMessageObject(byte[] data,byte type) throws Exception
	{
		
		if(CIMConstant.ProtobufType.C_H_RS == type)
		{
			HeartbeatResponse response = HeartbeatResponse.getInstance();
			logger.info(response.toString());
			SentBody body = new SentBody();
		    body.setKey(CIMConstant.CLIENT_HEARTBEAT);
		    body.setTimestamp(System.currentTimeMillis());
			return body;
		}
		
		if(CIMConstant.ProtobufType.SENTBODY == type)
		{
			SentBodyProto.Model bodyProto = SentBodyProto.Model.parseFrom(data);
	        SentBody body = new SentBody();
	        body.setKey(bodyProto.getKey());
	        body.setTimestamp(bodyProto.getTimestamp());
	        body.putAll(bodyProto.getDataMap());
	        logger.info(body.toString());
	        
	        return body;
		}
        return null;
	}

	/**
	 * 解析消息体长度
	 * @param lv
	 * @param hv
     * @return
     */
	private int getContentLength(byte lv,byte hv){
		 int l =  (lv & 0xff);
		 int h =  (hv & 0xff);
		 return (l| (h <<= 8));
	}
}
