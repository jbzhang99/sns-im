/**
 * Copyright 2013-2023 Xia Jun(3979434@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ***************************************************************************************
 *                                                                                     *
 *                        Website : http://www.farsunset.com                           *
 *                                                                                     *
 ***************************************************************************************
 */
package com.inga.server.sdk.filter;

import com.inga.server.sdk.constant.CIMConstant;
import com.inga.server.sdk.mdel.Protobufable;
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
