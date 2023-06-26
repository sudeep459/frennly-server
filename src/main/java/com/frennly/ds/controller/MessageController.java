package com.frennly.ds.controller;

import com.frennly.ds.exception.ChatException;
import com.frennly.ds.exception.MessageException;
import com.frennly.ds.exception.UserException;
import com.frennly.ds.model.Message;
import com.frennly.ds.model.User;
import com.frennly.ds.payload.request.SendMessageRequest;
import com.frennly.ds.payload.response.ApiResponse;
import com.frennly.ds.service.core.MessageService;
import com.frennly.ds.service.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest req, @RequestHeader("Authorization")String jwt) throws ChatException, UserException {
        User user = userService.findUserProfile(jwt);
        req.setUserId(user.getId());
        Message message = messageService.sendMessage(req);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }
    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatsMessageHandler(@PathVariable Integer chatId, @RequestHeader("Authorization")String jwt) throws ChatException, UserException {
        User user = userService.findUserProfile(jwt);
        List<Message> messages = messageService.getChatsMessages(chatId, user);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer messageId, @RequestHeader("Authorization")String jwt) throws UserException, MessageException {
        User user = userService.findUserProfile(jwt);
        messageService.deleteMessage(messageId, user);
        ApiResponse res = new ApiResponse("message deleted successfully", true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
