package com.socialmediaclone.socialmediaclone.controllers;

import com.socialmediaclone.socialmediaclone.dto.MessageDto;
import com.socialmediaclone.socialmediaclone.entities.Message;
import com.socialmediaclone.socialmediaclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class MessageController {

    private final UserService userService;

    @PostMapping(path = "/app/messages/send_message/{username}")
    public ResponseEntity<MessageDto> sendMessage(@PathVariable String username, @RequestBody Message message){
        return new ResponseEntity<>(this.userService.sendMessage(username, message), HttpStatus.OK);
    }

    @GetMapping(path = "/app/messages/{username}")
    public ResponseEntity<List<MessageDto>> getMessages(@PathVariable String username, Integer pageNumber, Integer pageSize){
        return new ResponseEntity<>(this.userService.getMessages(username, pageNumber, pageSize), HttpStatus.OK);
    }
}
