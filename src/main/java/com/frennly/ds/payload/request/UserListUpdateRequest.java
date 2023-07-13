package com.frennly.ds.payload.request;

import com.frennly.ds.model.Chat;
import com.frennly.ds.model.User;
import com.frennly.ds.payload.response.ChatResponse;
import com.frennly.ds.payload.response.MessageResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListUpdateRequest {
    private Integer userId;
    private ChatResponse chat;
}
