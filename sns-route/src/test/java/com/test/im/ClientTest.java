package com.test.im;

import com.inga.server.sdk.constant.CIMConstant;
import com.inga.server.sdk.mdel.Message;
import com.inga.server.sdk.mdel.SentBody;
import com.inga.util.im.ClientInboundHandler;

import java.util.Random;

/**
 * Created by abing on 2017/7/4.
 */
public class ClientTest {

    public static void main(String[] args) {
        ClientInboundHandler handler = new ClientInboundHandler();
        handler.init("127.0.0.1" , 23450);


        while (true){
            Message message = new Message();
            message.setTitle("this is title");
            message.setContent("this is content : " + new Random(100).nextInt());
            message.setReceiver("1001");
            message.setSender("1002");
            handler.push(message);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
