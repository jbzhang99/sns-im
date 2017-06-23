package com.inga.web;

import com.alibaba.fastjson.JSON;
import com.inga.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by abing on 2017/6/19.
 */
@Controller
@RequestMapping("/im/message")
public class MessageController {

    @RequestMapping("/sendMsg")
    @ResponseBody
    public String sendMsg(String id , String name){

        User user = new User();
        user.setId(id);
        user.setName(name);

        return JSON.toJSONString(user);
    }
}
