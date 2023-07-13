package com.frennly.ds.service.core;

import com.frennly.ds.exception.ChatException;
import com.frennly.ds.exception.MessageException;
import com.frennly.ds.exception.UserException;
import com.frennly.ds.model.Message;
import com.frennly.ds.model.User;
import com.frennly.ds.payload.request.SendMessageRequest;
import com.frennly.ds.payload.response.MessageResponse;

import java.util.List;

public interface MessageService {

    public Message sendMessage(SendMessageRequest req) throws UserException, ChatException;

    public List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException, UserException;

    public Message findMessageById(Integer messageId) throws MessageException;
    public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException;

}
