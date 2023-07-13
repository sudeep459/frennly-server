package com.frennly.ds.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {

    // user id of the therapist. A therapist cannot start the chat.
    private Integer userId;

}
