package com.frennly.ds.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Slf4j
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<String> HomeController() {
        return ResponseEntity.status(HttpStatus.OK).body("welcome to frennly api");
    }
}
