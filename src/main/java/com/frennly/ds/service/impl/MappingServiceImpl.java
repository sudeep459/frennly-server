package com.frennly.ds.service.impl;

import com.frennly.ds.model.Chat;
import com.frennly.ds.model.Message;
import com.frennly.ds.model.User;
import com.frennly.ds.payload.response.ChatResponse;
import com.frennly.ds.payload.response.MessageResponse;
import com.frennly.ds.payload.response.UserDetailsResponse;
import com.frennly.ds.service.core.MappingService;
import org.springframework.stereotype.Service;

@Service
public class MappingServiceImpl implements MappingService {
    @Override
    public ChatResponse mapChatToChatResponse(Chat chat) {
        ChatResponse chatResponse = new ChatResponse();
        if(chat.getId()!=null)chatResponse.setId(chat.getId());
        if(chat.getUser()!=null)chatResponse.setUser(this.mapUsertoUserResponse(chat.getUser()));
        if(chat.getTherapist()!=null)chatResponse.setTherapist(this.mapUsertoUserResponse(chat.getTherapist()));
        if(chat.getLatestMessage()!=null)chatResponse.setLatestMessage(this.mapMessagetoMessageResponse(chat.getLatestMessage()));
        if(chat.getStartedOn()!=null)chatResponse.setStartedOn(chat.getStartedOn());
        if(chat.getCreatedOn()!=null)chatResponse.setCreatedOn(chat.getCreatedOn());
        return chatResponse;
    }

    @Override
    public MessageResponse mapMessagetoMessageResponse(Message message) {
        MessageResponse messageResponse = new MessageResponse();
        if(message.getId()!=null)messageResponse.setId(message.getId());
        if(message.getContent()!=null)messageResponse.setContent(message.getContent());
        if(message.getDate()!=null)messageResponse.setDate(message.getDate());
        if(message.getTime()!=null)messageResponse.setTime(message.getTime());
        if(message.getSender()!=null)messageResponse.setSender(this.mapUsertoUserResponse(message.getSender()));
        if(message.getChat()!=null)messageResponse.setChat(this.mapChatToChatResponse(message.getChat()));
        return messageResponse;
    }

    @Override
    public UserDetailsResponse mapUsertoUserResponse(User user) {
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        if(user.getId()!=null)userDetailsResponse.setId(user.getId());
        if(user.getUsername()!=null)userDetailsResponse.setUsername(user.getUsername());
        if(user.getEmail()!=null)userDetailsResponse.setEmail(user.getEmail());
        if(user.getProfileImage()!=null)userDetailsResponse.setProfileImage(user.getProfileImage());
        if(user.getUserType()!=null)userDetailsResponse.setUserType(user.getUserType());
        return userDetailsResponse;
    }
}
