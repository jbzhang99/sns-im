package com.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by abing on 2017/7/5.
 */
public class SpringRun {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        context.start();

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
