package com.frennly.ds.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {
    private Integer id;

    private UserDetailsResponse user;

    private UserDetailsResponse therapist;

    private MessageResponse latestMessage;

    private Timestamp createdOn;

    private Timestamp startedOn;
}
