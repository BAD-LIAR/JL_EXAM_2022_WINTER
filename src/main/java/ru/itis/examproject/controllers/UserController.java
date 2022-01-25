package ru.itis.examproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.examproject.dto.EmailPasswordDto;
import ru.itis.examproject.dto.TokenDto;
import ru.itis.examproject.service.LoginService;

@RestController
public class UserController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody EmailPasswordDto emailPassword) {
        return ResponseEntity.ok(loginService.login(emailPassword));
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody EmailPasswordDto emailPasswordDto){
        loginService.createUser(emailPasswordDto);
        return ResponseEntity.ok("Suscess");
    }

}
