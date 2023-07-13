package com.frennly.ds.service.core;

import com.frennly.ds.exception.ChatException;
import com.frennly.ds.exception.UserException;
import com.frennly.ds.model.Chat;
import com.frennly.ds.model.User;
import com.frennly.ds.payload.response.ChatResponse;

import java.util.List;

public interface ChatService {

    public Chat createChat(User reqUser, Integer userId2) throws UserException, ChatException;

    public Chat findChatById(Integer chatId) throws ChatException;
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException;
    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException;
    public void updateChat(Chat chat);
}
