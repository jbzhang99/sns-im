package com.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by abing on 2017/7/1.
 */
public class MQCunsumerTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:mq.xml");
        context.start();
    }
}
