package com.frennly.ds.service.impl;

import com.frennly.ds.exception.ChatException;
import com.frennly.ds.exception.MessageException;
import com.frennly.ds.exception.UserException;
import com.frennly.ds.model.Chat;
import com.frennly.ds.model.Message;
import com.frennly.ds.model.User;
import com.frennly.ds.payload.request.SendMessageRequest;
import com.frennly.ds.repository.MessageRepository;
import com.frennly.ds.service.core.ChatService;
import com.frennly.ds.service.core.MessageService;
import com.frennly.ds.service.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;

    @Override
    public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {
        User user = userService.findUserById(req.getUserId());
        Chat chat = chatService.findChatById(req.getChatId());

        Message message = new Message();
        message.setChat(chat);
        message.setUser(user);
        message.setContent(req.getContent());
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException, UserException {
        Chat chat = chatService.findChatById(chatId);

        if(!chat.getUsers().contains(reqUser)) {
            throw new UserException("you are not related to this chat " + chat.getId());
        }

        List<Message> messages = messageRepository.findByChatId(chat.getId());
        return messages;
    }

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {
        Optional<Message> opt = messageRepository.findById(messageId);
        if(opt.isPresent()) {
            return opt.get();
        }
        else {
            throw new MessageException("message not found with id " + messageId);
        }
    }

    @Override
    public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException {
        Message message = findMessageById(messageId);

        if(message.getUser().getId().equals(reqUser.getId())) {
            messageRepository.deleteById(messageId);
        }
        else {
            throw new UserException("you can't delete another user's message - " + reqUser.getUsername());
        }
    }
}