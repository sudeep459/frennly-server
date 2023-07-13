package com.frennly.ds.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"chat"})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String content;

    private String date;

    private String time;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName="id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName="id")
    @JsonIgnoreProperties("latestMessage")
    private Chat chat;
}
