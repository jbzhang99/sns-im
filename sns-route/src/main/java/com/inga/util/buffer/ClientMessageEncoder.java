
package com.inga.util.buffer;


import com.inga.constant.CIMConstant;
import com.inga.model.Protobufable;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 *  客户端消息发送前进行编码,可在此加密消息
 *
 */
public class ClientMessageEncoder extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object message, ByteBuf out) throws Exception {

		if(message instanceof Protobufable){
	        Protobufable data = (Protobufable) message;
	        byte[] byteArray = data.getByteArray();
	        out.writeBytes(createHeader(data.getType(),byteArray.length));
	        out.writeBytes(byteArray);
	    }
		
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
