package com.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.Random;

/**
 * Created by abing on 2017/7/1.
 */
public class MQProducerTest {
    public static final String brokrnURL = "tcp://localhost:61616";
    public static void main(String[] args) throws InterruptedException {

        ActiveMQConnectionFactory targetConnectFactpry = new ActiveMQConnectionFactory(brokrnURL);

        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(targetConnectFactpry);
        pooledConnectionFactory.setMaxConnections(10);

        SingleConnectionFactory connectionFactory = new SingleConnectionFactory(pooledConnectionFactory);

        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);

        ActiveMQQueue queue = new ActiveMQQueue("testQueue1");
        jmsTemplate.setDefaultDestination(queue);

        while (true){

            Random random = new Random(100);
            jmsTemplate.convertAndSend("this ios init work : " + random.nextInt());

            Thread.sleep(1000);
        }




    }
}

