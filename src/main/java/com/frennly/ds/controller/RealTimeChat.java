package com.frennly.ds.controller;

import com.frennly.ds.exception.ChatException;
import com.frennly.ds.model.Chat;
import com.frennly.ds.model.Message;
import com.frennly.ds.model.User;
import com.frennly.ds.payload.request.UserListUpdateRequest;
import com.frennly.ds.payload.response.ChatResponse;
import com.frennly.ds.payload.response.MessageResponse;
import com.frennly.ds.service.core.ChatService;
import com.frennly.ds.service.core.MappingService;
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

    @Autowired
    private ChatService chatService;

    @Autowired
    private MappingService mappingService;

    @MessageMapping("/message")
    @SendTo("/group/public")
    public MessageResponse recieveMessage(@Payload Message message) {

        simpMessagingTemplate.convertAndSend("/group/" + message.getChat().getId().toString(), message);
        return mappingService.mapMessagetoMessageResponse(message);
    }

    @MessageMapping("/user-list")
    @SendToUser("/public")
    public ChatResponse recieveUserList(@Payload UserListUpdateRequest req) throws ChatException {
        ChatResponse chatResponse = mappingService.mapChatToChatResponse(chatService.findChatById(req.getChat().getId()));
        simpMessagingTemplate.convertAndSend("/users/" + req.getUserId().toString(), chatResponse);
        return chatResponse;
    }
}
