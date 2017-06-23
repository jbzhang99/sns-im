package com.test;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * Created by abing on 2017/6/19.
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        while (true){
            boolean isSSL = true;
            String host = "smtp.qq.com";
            int port = 465;
            String from = "1500811059@qq.com";
            String to = "null";
            String username = "1500811059@qq.com";
            String password = "null";

            try {
                Email email = new SimpleEmail();
                email.setSSLOnConnect(isSSL);
                email.setHostName(host);
                email.setSmtpPort(port);
                email.setAuthentication(username, password);
                email.setFrom(from);
                email.addTo(to);
                email.setSubject("主题");
                email.setMsg("内容");
                email.send();
            } catch (EmailException e) {
                e.printStackTrace();
            }

            System.out.println("发送完毕！");
            Thread.sleep(5000);
        }

    }
}
