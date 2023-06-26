package com.frennly.ds.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendMessageRequest {
    private Integer userId;
    private Integer chatId;
    private String content;
}
