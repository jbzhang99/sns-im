package com.inga.server.sdk.filter;

import com.inga.constant.CIMConstant;
import com.inga.model.Protobufable;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 服务端发送消息前编码
 */
public class ServerMessageEncoder extends MessageToByteEncoder<Object> {

	protected final Logger logger = LoggerFactory.getLogger(ServerMessageEncoder.class.getSimpleName());

	@Override
	protected void encode(ChannelHandlerContext ctx, Object message, ByteBuf out) throws Exception {
		
		
		if(message instanceof Protobufable){
    		
	        Protobufable data = (Protobufable) message;
	        byte[] byteArray = data.getByteArray();
	        		
		        	
	        out.writeBytes(createHeader(data.getType(),byteArray.length));
	        out.writeBytes(byteArray);
	        		
	    }
		
		logger.info(message.toString());
	}
	
	/**
	 * 消息体最大为65535
	 * @param type
	 * @param length
	 * @return
	 */
	private byte[] createHeader(byte type,int length){
		byte[] header = new byte[CIMConstant.DATA_HEADER_LENGTH];
		header[0] = type;
		header[1] = (byte) (length & 0xff);
        header[2] = (byte) ((length >> 8) & 0xff);
		return header;
	}
	 

}
