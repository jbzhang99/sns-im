package com.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by abing on 2017/7/5.
 */
public class SpringRun {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-im-server.xml");
        context.start();
        Thread.sleep(Integer.MAX_VALUE);

    }
}
