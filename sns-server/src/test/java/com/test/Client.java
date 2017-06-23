package com.test;


import com.inga.server.sdk.constant.CIMConstant;
import com.inga.server.sdk.mdel.proto.ReplyBodyProto;
import com.inga.server.sdk.mdel.proto.SentBodyProto;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by abing on 2017/5/11.
 */
public class Client {

    static Socket socket;
    public static void main(String[] args) throws UnknownHostException, IOException {

        socket = new Socket("127.0.0.1",23450);
        socket.setTcpNoDelay(true);
        sendBindRequest();//发送绑定请求

        while(true){
            receiveMessage();//接受消息
        }
    }


    public static void sendBindRequest() throws IOException
    {
        OutputStream write =  socket.getOutputStream();
        SentBodyProto.Model.Builder builder = SentBodyProto.Model.newBuilder();
        builder.setKey(CIMConstant.MESSAGE_TYPE.CLIENT_BIND);
        builder.setTimestamp(System.currentTimeMillis());
        builder.putData("account", "10000");
        builder.putData("channel", "java");
        builder.putData("deviceId", "JVM");
        builder.putData("device_model", "1.0");

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
