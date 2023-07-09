package com.frennly.ds.payload.request;

import com.frennly.ds.model.Chat;
import com.frennly.ds.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListUpdateRequest {
    private Integer userId;
    private Chat chat;
}
