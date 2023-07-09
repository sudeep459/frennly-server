package com.frennly.ds.controller;

import com.frennly.ds.model.Chat;
import com.frennly.ds.model.Message;
import com.frennly.ds.model.User;
import com.frennly.ds.payload.request.UserListUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class RealTimeChat {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/group/public")
    public Message recieveMessage(@Payload Message message) {

        simpMessagingTemplate.convertAndSend("/group/" + message.getChat().getId().toString(), message);
        return message;
    }

    @MessageMapping("/user-list")
    @SendToUser("/public")
    public Chat recieveUserList(@Payload UserListUpdateRequest req) {
        simpMessagingTemplate.convertAndSend("/user/" + req.getUserId().toString(), req.getChat());
        return req.getChat();
    }
}
