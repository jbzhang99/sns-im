package com.test.inga.im.client;

/**
 * Created by abing on 2017/7/4.
 */
public class TestClientHandler {

    public static void main(String[] args) {
        ClientHandler handler = new ClientHandler();
        handler.init("127.0.0.1", 23450);

        handler.channel.writeAndFlush("1");
    }
}
