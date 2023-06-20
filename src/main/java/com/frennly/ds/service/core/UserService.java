package com.frennly.ds.service.core;

import com.frennly.ds.payload.request.UpdateUserRequest;
import com.frennly.ds.exception.UserException;
import com.frennly.ds.model.User;

import java.util.List;

public interface UserService {

    public User findUserById(Integer id) throws UserException;
    public User findUserProfile(String jwt) throws UserException;
    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;
    public List<User> searchUser(String query);
    public User findUserByUsername(String username);
    public User findUserByEmail(String email);
    public User saveUser(User user);
}
