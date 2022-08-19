package com.rubric.service.auth.controller;

import com.rubric.service.auth.error.ErrorResponse;
import com.rubric.service.auth.dto.LoginDTO;
import com.rubric.service.auth.entity.User;
import com.rubric.service.auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User userToRegister) {
        User checkUser = userService.getUserByEmail(userToRegister.getEmail());
        if(checkUser != null) {
            return new ResponseEntity(new ErrorResponse(400, "Bad request, user exists", "/users/register"), HttpStatus.BAD_REQUEST);
        }
        userToRegister.setPassword(userService.hashPassword(userToRegister.getPassword()));
        User user = userService.createUser(userToRegister);
        log.info("User created with id " + user.getId());
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO userLogin) {
        String hash = userService.hashPassword(userLogin.getPassword());
        User user = userService.getUserByEmail(userLogin.getEmail());
        if(user == null) {
            return new ResponseEntity(new ErrorResponse(400, "Bad request, user doesn't exist", "/users/login"), HttpStatus.BAD_REQUEST);
        }
        if(!hash.equals(user.getPassword())) {
            return new ResponseEntity(new ErrorResponse(403, "Bad request, password incorrect", "/users/login"), HttpStatus.FORBIDDEN);
        }
        String token = userService.getJwt(user.getId());
        return new ResponseEntity<Map>(Collections.singletonMap("token", token), HttpStatus.OK);
    }

}
