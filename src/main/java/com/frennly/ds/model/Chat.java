package com.frennly.ds.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"latestMessage"})
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "therapist_id", referencedColumnName = "id")
    private User therapist;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "latest_message_id", referencedColumnName = "id")
    @JsonIgnoreProperties("chat")
    private Message latestMessage;

    private Timestamp createdOn;

    private Timestamp startedOn;

    private boolean isActive;
}
