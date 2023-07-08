package com.frennly.ds.service.impl;

import com.frennly.ds.exception.ChatException;
import com.frennly.ds.exception.UserException;
import com.frennly.ds.model.Chat;
import com.frennly.ds.model.User;
import com.frennly.ds.repository.ChatRepository;
import com.frennly.ds.service.core.ChatService;
import com.frennly.ds.service.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserService userService;

    @Override
    public Chat createChat(User reqUser, Integer userId2) throws UserException {
        User user = userService.findUserById(userId2);
        Chat isChat = chatRepository.findSingleChatByUserIds(user, reqUser);

        if(isChat != null) {
            return isChat;
        }

        Chat chat = new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        return chatRepository.save(chat);
    }
    @Override
    public Chat findChatById(Integer chatId) throws ChatException {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if(chat.isPresent()) {
            return chat.get();
        }
        else {
            throw new ChatException("Chat not found with id " + chatId);
        }
    }

    @Override
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        if(user == null) {
            throw new UserException("User not found with id " + userId);
        }
        return chatRepository.findChatByUserid(user.getId());
    }

    @Override
    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
        Optional<Chat> opt = chatRepository.findById(chatId);

        if(opt.isPresent()) {
            Chat chat = opt.get();
            if(chat.getCreatedBy().getId().equals(userId)) {
                chatRepository.deleteById(chat.getId());
            } else {
                throw new ChatException("You are not allowed to delete this chat");
            }
        }
    }
}
