package com.test.client;


import com.inga.constant.CIMConstant;
import com.inga.model.proto.ReplyBodyProto;
import com.inga.model.proto.SentBodyProto;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * Date  2018/1/15
 * Time  上午10:20
 */
public class ClientPull {

    static Socket socket;
    public static void main(String[] args) throws UnknownHostException, IOException {

        socket = new Socket("localhost",23450);
        socket.setTcpNoDelay(true);
        sendBindRequest();//发送绑定请求


    }


    public static void sendBindRequest() throws IOException
    {
        OutputStream write =  socket.getOutputStream();
        SentBodyProto.Model.Builder builder = SentBodyProto.Model.newBuilder();
        builder.setKey(CIMConstant.MESSAGE_TYPE.PULL_MSG);
        builder.setTimestamp(System.currentTimeMillis());
        builder.putData(CIMConstant.CONTENT , "你好10000");
        builder.putData(CIMConstant.TITLE , "聊天小消息");
        builder.putData(CIMConstant.RECEIVER , "10000");
        builder.putData(CIMConstant.ACCOUNT , "10001");


        byte[] data = builder.build().toByteArray();

        /**
         * 消息头
         */
        byte type = 3;
        write.write(createHeader(type,data.length));//消息类型为SentBody
        write.write(data);
    }


    /**
     * 消息体最大为65535
     * @param type
     * @param length
     * @return
     */
    private static byte[] createHeader(byte type,int length){
        byte[] header = new byte[3];
        header[0] = type;
        header[1] = (byte) (length & 0xff);
        header[2] = (byte) ((length >> 8) & 0xff);
        return header;
    }



    private static int getContentLength(byte lv,byte hv){
        int l =  (lv & 0xff);
        int h =  (hv & 0xff);
        return (l| (h <<= 8));
    }

    public static void receiveMessage() throws IOException
    {
        byte[] bytes = new byte[1024];
        socket.getInputStream().read(bytes);
        byte type = bytes[0];
        byte lv = bytes[1];
        byte hv = bytes[2];
        int legnth = getContentLength(lv,hv);
        byte[] data = new byte[legnth];

        System.arraycopy(bytes, 3, data, 0, legnth);

        if(type==4){
            ReplyBodyProto.Model bodyProto = ReplyBodyProto.Model.parseFrom(data);
            System.out.println("收到服务端返回:"+bodyProto.getCode());
            System.out.println(bodyProto.getMessage());
            System.out.println(bodyProto.getKey());
            System.out.println(bodyProto.getDataMap().values());
        }

    }
}
