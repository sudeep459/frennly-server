package com.frennly.ds.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String content;
    private String date;
    private String time;
//    private ZonedDateTime timestamp;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName="id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName="id")
    private Chat chat;
}
