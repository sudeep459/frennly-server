package com.frennly.ds.payload.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    private Integer id;

    private String content;

    private String date;

    private String time;

    private UserDetailsResponse sender;

    private ChatResponse chat;
}
