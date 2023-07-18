package com.frennly.ds.controller;

import com.frennly.ds.config.TokenProvider;
import com.frennly.ds.exception.UserException;
import com.frennly.ds.model.User;
import com.frennly.ds.payload.request.LoginRequest;
import com.frennly.ds.payload.response.AuthResponse;
import com.frennly.ds.service.core.CustomUserService;
import com.frennly.ds.service.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CustomUserService customUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();

        User isUsername = userService.findUserByUsername(username);
        if(isUsername != null) {
            throw new UserException("username already exists : " + username);
        }
        User isUserEmail = userService.findUserByEmail(email);
        if(isUserEmail != null) {
            throw new UserException("email already used with a different account : " + email);
        }

        User createdUser = new User();
        createdUser.setUsername(username);
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setUserType(user.getUserType());
        createdUser = userService.saveUser(createdUser);

        userService.setActiveUserList(createdUser);


        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse(jwt, true);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) throws UserException {
        String username = req.getUsername();
        String password = req.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse(jwt, true);

//        User user = userService.findUserProfile(jwt);
//        userService.setActiveUserList(user);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(res);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("invalid username");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("invalid password or username");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
