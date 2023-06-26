package com.frennly.ds.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ChatException extends Exception {
    public ChatException(String message) {
        super(message);
    }
}
