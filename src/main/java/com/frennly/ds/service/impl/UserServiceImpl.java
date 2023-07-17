package com.frennly.ds.service.impl;

import com.frennly.ds.config.TokenProvider;
import com.frennly.ds.enums.UserType;
import com.frennly.ds.payload.request.PreferredTimingsRequest;
import com.frennly.ds.payload.request.UpdateUserRequest;
import com.frennly.ds.exception.UserException;
import com.frennly.ds.model.User;
import com.frennly.ds.payload.response.UserDetailsResponse;
import com.frennly.ds.repository.UserRepository;
import com.frennly.ds.service.core.MappingService;
import com.frennly.ds.service.core.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MappingService mappingService;

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public User findUserById(Integer id) throws UserException {
        log.info("User service - findUserById");
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        }else {
            throw new UserException("User not found with id " + id);
        }
    }

    @Override
    public User findUserProfile(String jwt) throws UserException {
        String username = tokenProvider.getUsernameFromToken(jwt);

        if(username == null)
            throw new BadCredentialsException("received invalid token---");

        User user = userRepository.findByUsername(username);

        if(user == null)
            throw new UserException("user not found with username " + username);

        return user;
    }



    @Override
    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException {
        User user = findUserById(userId);
        if(user == null){
            throw new UserException("user not found with id " + userId);
        }
        if(req.getUsername() != null) {
            user.setUsername(req.getUsername());
        }
        return userRepository.save(user);
    }

    @Override
    public List<UserDetailsResponse> searchUser(String query, User reqUser) {
        log.info("searchuser service  - " + query);
        List<User> users = userRepository.searchUser(query+"%", UserType.THERAPIST).stream().filter(user -> !user.getId().equals(reqUser.getId())).collect(Collectors.toList());
        return users.stream().map(mappingService::mapUsertoUserResponse).collect(Collectors.toList());
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        Random random = new Random();
        String profileImage = "/images/avatar-" +  random.nextInt(1,8)+".svg";
        user.setProfileImage(profileImage);
        return userRepository.save(user);
    }

    @Override
    public List<UserDetailsResponse> findAllTherapists(User reqUser) throws UserException {
        List<User> therapists = null;
        if(reqUser.getUserType() == UserType.USER)
            therapists = userRepository.findByUserType(UserType.THERAPIST);
        else {
            throw new UserException("You have to be logged in as a user to view therapists");
        }
        return therapists.stream().map(mappingService::mapUsertoUserResponse).collect(Collectors.toList());
    }

    @Override
    public void addPreferredTimings(PreferredTimingsRequest req, String username) throws UserException {
        User user = userRepository.findByUsername(username);

        if(user == null) throw new UserException("User not found with username " + username);

        user.setPreferredTimings(req.getReq());

        userRepository.save(user);
    }


}
