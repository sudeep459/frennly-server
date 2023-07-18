package com.frennly.ds.service.impl;

import com.frennly.ds.enums.UserType;
import com.frennly.ds.exception.ChatException;
import com.frennly.ds.exception.UserException;
import com.frennly.ds.model.Chat;
import com.frennly.ds.model.User;
import com.frennly.ds.payload.response.ChatResponse;
import com.frennly.ds.payload.response.UserDetailsResponse;
import com.frennly.ds.repository.ChatRepository;
import com.frennly.ds.service.core.ChatService;
import com.frennly.ds.service.core.MessageService;
import com.frennly.ds.service.core.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserService userService;

    @Override
    public Chat createChat(User reqUser, Integer userId2) throws UserException, ChatException {
        User therapist = userService.findUserById(userId2);

        Chat isChat = chatRepository.findSingleChatByUserIds(reqUser,therapist);

        if(isChat != null) {
            return isChat;
        }

        if(therapist.getUserType()!= UserType.THERAPIST)
            throw new ChatException("Initiating a chat with another user is not allowed. Please provide a therapist id to continue");
        if(reqUser.getUserType()== UserType.THERAPIST)
            throw new ChatException("you cannot start a chat as a therapist. Wait for a user to start a chat");

        Chat chat = new Chat();
        chat.setUser(reqUser);
        chat.setTherapist(therapist);
        chat.setCreatedOn(Timestamp.from(Instant.now()));
        chat.setActive(true);

        userService.setActiveUserList(reqUser);

        return chatRepository.save(chat);
    }
    @Override
    public Chat findChatById(Integer chatId) throws ChatException {
        log.info("ChatService findChatById");
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
        List<Chat> chats = chatRepository.findChatByUserid(user.getId());
        log.info("ChatService findAllchats - " + chats);
        userService.setActiveUserList(user);

        return chats;
    }

    @Override
    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
        Optional<Chat> opt = chatRepository.findById(chatId);

        if(opt.isPresent()) {
            Chat chat = opt.get();
            if(chat.getUser().getId().equals(userId)) {
                chatRepository.deleteById(chat.getId());
            } else {
                throw new ChatException("You are not allowed to delete this chat");
            }
        }
    }



    @Override
    public void updateChat(Chat chat) {
        chatRepository.save(chat);
    }
}
