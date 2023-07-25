package com.frennly.ds.controller;

import com.frennly.ds.exception.ChatException;
import com.frennly.ds.exception.EmailException;
import com.frennly.ds.exception.MessageException;
import com.frennly.ds.exception.UserException;
import com.frennly.ds.model.Message;
import com.frennly.ds.model.User;
import com.frennly.ds.payload.request.SendMessageRequest;
import com.frennly.ds.payload.response.ApiResponse;
import com.frennly.ds.payload.response.MessageResponse;
import com.frennly.ds.payload.response.UserDetailsResponse;
import com.frennly.ds.service.core.MappingService;
import com.frennly.ds.service.core.MessageService;
import com.frennly.ds.service.core.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
@Slf4j
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private MappingService mappingService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> sendMessageHandler(@RequestBody SendMessageRequest req, @RequestHeader("Authorization")String jwt) throws ChatException, UserException, EmailException {
        User user = userService.findUserProfile(jwt);
        req.setUserId(user.getId());
        Message message = messageService.sendMessage(req);
        log.info("User - " + user + "\n" + "message = " + message);
        return new ResponseEntity<>(mappingService.mapMessagetoMessageResponse(message), HttpStatus.OK);
    }
    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<MessageResponse>> getChatsMessageHandler(@PathVariable Integer chatId, @RequestHeader("Authorization")String jwt) throws ChatException, UserException {
        User user = userService.findUserProfile(jwt);
        List<Message> messages = messageService.getChatsMessages(chatId, user);
        log.info("User: " + user + "\n" + "Messages : " + messages);
        return new ResponseEntity<>(messages.stream().map(mappingService::mapMessagetoMessageResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable Integer messageId, @RequestHeader("Authorization")String jwt) throws UserException, MessageException {
        User user = userService.findUserProfile(jwt);
        messageService.deleteMessage(messageId, user);
        ApiResponse res = new ApiResponse("message deleted successfully", true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
