package com.java.projects.controller;

import com.java.projects.dto.NewUserDto;
import com.java.projects.dto.UserDto;
import com.java.projects.model.User;
import com.java.projects.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
                          PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/user/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid NewUserDto newUser) throws Exception{
        if(!newUser.getPassword().equals(newUser.getMatchingPassword()))
            throw new Exception("Podane hasła różnią się!");
        User user = Mapper.mapToEntity(newUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.registerUser(user);
        return new ResponseEntity<>(Mapper.maptoDto(user), HttpStatus.CREATED);
    }
}
