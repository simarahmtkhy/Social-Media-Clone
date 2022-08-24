package com.socialmediaclone.socialmediaclone.controllers;


import com.socialmediaclone.socialmediaclone.entities.User;
import com.socialmediaclone.socialmediaclone.services.MailService;
import com.socialmediaclone.socialmediaclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    private final MailService mailService;


    @PostMapping(path = "/signup")
    public ResponseEntity<String> addUser(@RequestBody User user){
        System.out.println(user.getPassword());
        this.userService.addUser(user);
        this.mailService.sendMail(user.getEmail(), user.getVerificationToken());
        return new ResponseEntity<>("Signed Up Successfully", HttpStatus.OK);
    }
    @GetMapping(path = "/users/{token}")
    public ResponseEntity<String> verifyUser(@PathVariable String token) {
        this.userService.verifyUser(token);
        return new ResponseEntity<>("Verified User", HttpStatus.OK);
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<String> refreshToken() {
        return new ResponseEntity<>("Got the refresh token", HttpStatus.OK);
    }
}
