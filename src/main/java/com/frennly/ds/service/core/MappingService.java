package com.frennly.ds.service.core;

import com.frennly.ds.model.Chat;
import com.frennly.ds.model.Message;
import com.frennly.ds.model.User;
import com.frennly.ds.payload.response.ChatResponse;
import com.frennly.ds.payload.response.MessageResponse;
import com.frennly.ds.payload.response.UserDetailsResponse;

public interface MappingService {
    public ChatResponse mapChatToChatResponse(Chat chat);

    public MessageResponse mapMessagetoMessageResponse(Message message);

    public UserDetailsResponse mapUsertoUserResponse(User user) ;
}
