package com.frennly.ds.controller;

import com.frennly.ds.exception.ChatException;
import com.frennly.ds.exception.UserException;
import com.frennly.ds.model.Chat;
import com.frennly.ds.model.User;
import com.frennly.ds.payload.request.ChatRequest;
import com.frennly.ds.payload.response.ApiResponse;
import com.frennly.ds.payload.response.ChatResponse;
import com.frennly.ds.payload.response.UserDetailsResponse;
import com.frennly.ds.service.core.ChatService;
import com.frennly.ds.service.core.MappingService;
import com.frennly.ds.service.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;
    @Autowired
    private MappingService mappingService;

    @PostMapping("/create")
    public ResponseEntity<ChatResponse> createChatHandler(@RequestBody ChatRequest chatRequest, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User reqUser  = userService.findUserProfile(jwt);

        Chat chat = chatService.createChat(reqUser, chatRequest.getUserId());
        return new ResponseEntity<>(mappingService.mapChatToChatResponse(chat), HttpStatus.OK);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatResponse> findChatByIdHandler(@PathVariable Integer chatId, @RequestHeader("Authorization") String jwt) throws ChatException {
        Chat chat = chatService.findChatById(chatId);
        return ResponseEntity.status(HttpStatus.OK).body(mappingService.mapChatToChatResponse(chat));
    }

    @GetMapping("/user")
    public ResponseEntity<List<ChatResponse>> findChatsByUserIdHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = userService.findUserProfile(jwt);
        List<Chat> chats = chatService.findAllChatByUserId(reqUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body(chats.stream().map(mappingService::mapChatToChatResponse).collect(Collectors.toList()));
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<ApiResponse> deleteChat(@PathVariable Integer chatId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {
        User reqUser = userService.findUserProfile(jwt);
        chatService.deleteChat(chatId, reqUser.getId());

        ApiResponse res = new ApiResponse("chat deleted successfully", true);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
