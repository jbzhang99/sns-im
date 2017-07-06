package com.inga.util.mq;

/*
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
*/

/**
 * Created by abing on 2017/6/28.
 */
public class TestComsumer{

}
/*public class TestComsumer implements MessageListener {

    public void onMessage(Message message) {

        if (message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            try {
                String text = textMessage.getText();
                System.out.println(text);
            }catch (JMSException e){
                e.printStackTrace();
            }
        }
    }
}*/
