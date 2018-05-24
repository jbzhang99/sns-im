package com.test.inga.im.client;

/**
 *
 * Date  2018/1/15
 * Time  上午10:21
 * Author bingbing.wang@corp.elong.com
 */
public class TestClientHandler {

    public static void main(String[] args) {
        ClientHandler handler = new ClientHandler();
        handler.init("127.0.0.1", 23450);

        handler.channel.writeAndFlush("1");
    }
}
