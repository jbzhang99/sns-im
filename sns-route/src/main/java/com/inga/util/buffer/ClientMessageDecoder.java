
package com.inga.util.buffer;

import com.google.protobuf.InvalidProtocolBufferException;
import com.inga.server.sdk.constant.CIMConstant;
import com.inga.server.sdk.mdel.HeartbeatRequest;
import com.inga.server.sdk.mdel.Message;
import com.inga.server.sdk.mdel.ReplyBody;
import com.inga.server.sdk.mdel.proto.MessageProto;
import com.inga.server.sdk.mdel.proto.ReplyBodyProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 客户端消息解码
 */
public class ClientMessageDecoder extends ByteToMessageDecoder {

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

		Object message = mappingMessageObject(dataBytes, conetnType);
		
		if(message!=null){
			queue.add(message);
		}

	}

	private Object mappingMessageObject(byte[] bytes, byte type) throws InvalidProtocolBufferException {

		if (CIMConstant.ProtobufType.S_H_RQ == type) {
			HeartbeatRequest request = HeartbeatRequest.getInstance();
			return request;
		}

		if (CIMConstant.ProtobufType.REPLYBODY == type) {
			ReplyBodyProto.Model bodyProto = ReplyBodyProto.Model.parseFrom(bytes);
			ReplyBody body = new ReplyBody();
			body.setKey(bodyProto.getKey());
			body.setTimestamp(bodyProto.getTimestamp());
			body.putAll(bodyProto.getDataMap());
			body.setCode(bodyProto.getCode());
			body.setMessage(bodyProto.getMessage());

			return body;
		}

		if (CIMConstant.ProtobufType.MESSAGE == type) {
			MessageProto.Model bodyProto = MessageProto.Model.parseFrom(bytes);
			Message message = new Message();
			message.setMid(bodyProto.getMid());
			message.setAction(bodyProto.getAction());
			message.setContent(bodyProto.getContent());
			message.setSender(bodyProto.getSender());
			message.setReceiver(bodyProto.getReceiver());
			message.setTitle(bodyProto.getTitle());
			message.setExtra(bodyProto.getExtra());
			message.setTimestamp(bodyProto.getTimestamp());
			message.setFormat(bodyProto.getFormat());

			return message;
		}

		return null;

	}

	/**
	 * 解析消息体长度
	 * 
	 * @return
	 */
	private int getContentLength(byte lv, byte hv) {
		int l = (lv & 0xff);
		int h = (hv & 0xff);
		return (l | (h <<= 8));
	}

}
