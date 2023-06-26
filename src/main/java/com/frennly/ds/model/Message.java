package com.frennly.ds.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String content;
    private LocalDateTime timestamp;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName="id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName="id")
    private Chat chat;
}
