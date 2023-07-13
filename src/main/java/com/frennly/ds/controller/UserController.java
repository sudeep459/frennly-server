package com.frennly.ds.controller;

import com.frennly.ds.enums.UserType;
import com.frennly.ds.exception.UserException;
import com.frennly.ds.model.User;
import com.frennly.ds.payload.request.UpdateUserRequest;
import com.frennly.ds.payload.response.ApiResponse;
import com.frennly.ds.payload.response.UserDetailsResponse;
import com.frennly.ds.service.core.MappingService;
import com.frennly.ds.service.core.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MappingService mappingService;

    @GetMapping("/profile")
    public ResponseEntity<UserDetailsResponse> getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        UserDetailsResponse res = mappingService.mapUsertoUserResponse(user);
        log.info("user profile found: " + user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<UserDetailsResponse>> searchUserHandler(@PathVariable("name") String query, @RequestHeader("Authorization") String token) throws UserException {
        User reqUser = userService.findUserProfile(token);

        log.info("searchUserHandler " + query );
        List<UserDetailsResponse> users = userService.searchUser(query, reqUser);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/get-therapists")
    public ResponseEntity<List<UserDetailsResponse>> getAllTherapists(@RequestHeader("Authorization") String token) throws UserException {
        User reqUser = userService.findUserProfile(token);
        List<UserDetailsResponse> therapists = userService.findAllTherapists(reqUser);
        log.info("UserController getAllTherapists - " + therapists);
        return ResponseEntity.status(HttpStatus.OK).body(therapists);

    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest req, @RequestHeader("Authorization") String token) throws UserException {
        User user = userService.findUserProfile(token);
        userService.updateUser(user.getId(), req);
        ApiResponse res = new ApiResponse("user updated successfully", true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
    }

}
