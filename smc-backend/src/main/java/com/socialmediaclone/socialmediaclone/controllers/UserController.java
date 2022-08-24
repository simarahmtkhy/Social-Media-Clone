package com.socialmediaclone.socialmediaclone.controllers;

import com.socialmediaclone.socialmediaclone.dto.CommentDto;
import com.socialmediaclone.socialmediaclone.dto.FollowRequestDto;
import com.socialmediaclone.socialmediaclone.dto.MessageDto;
import com.socialmediaclone.socialmediaclone.dto.UserDto;
import com.socialmediaclone.socialmediaclone.entities.Comment;
import com.socialmediaclone.socialmediaclone.entities.Message;
import com.socialmediaclone.socialmediaclone.services.ImageService;
import com.socialmediaclone.socialmediaclone.services.UserService;
import com.socialmediaclone.socialmediaclone.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    private final ImageService imageService;

    private final VideoService videoService;

    @Autowired
    public UserController(UserService userService, ImageService imageService, VideoService videoService) {
        this.userService = userService;
        this.imageService = imageService;
        this.videoService = videoService;
    }


    @GetMapping(path = "/app/search/{search}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String search) {
        return new ResponseEntity<>(this.userService.search(search), HttpStatus.OK);
    }

    @PostMapping(path = "/app/follow/{username}")
    public ResponseEntity<FollowRequestDto> followRequest(@PathVariable String username){
        return new ResponseEntity<>(this.userService.followRequest(username), HttpStatus.OK);
    }

    @PostMapping(path = "/app/accept/{reqId}")
    public void acceptRequest(@PathVariable Long reqId) {
        userService.acceptRequest(reqId);
    }

    @DeleteMapping(path = "/app/reject/{reqId}")
    public void rejectRequest(@PathVariable Long reqId) {
        userService.rejectRequest(reqId);
    }

    @DeleteMapping(path = "/app/cancel/{reqId}")
    public void cancelRequest(@PathVariable Long reqId) {
        this.userService.cancelReq(reqId);
    }

    @GetMapping(path = "/app/requests")
    public List<FollowRequestDto> getRequests() {
        return userService.getRequests();
    }

    @DeleteMapping(path = "/app/unfollow/{username}")
    public void unfollow(@PathVariable String username){
        this.userService.unfollow(username);
    }

    @GetMapping(path = "/app/isfollower/{username}")
    public ResponseEntity<Boolean> isFollower(@PathVariable String username) {
        return new ResponseEntity<>(this.userService.isFollowerCheck(username), HttpStatus.OK);
    }

    @GetMapping(path = "/app/haspendingreq/{username}")
    public ResponseEntity<Boolean> hasPendingRequest(@PathVariable String username) {
        return new ResponseEntity<>(userService.hasPendingRequest(username), HttpStatus.OK);
    }

    @GetMapping(path = "app/user")
    public ResponseEntity<UserDto> getCurrentUser() {
        return new ResponseEntity<>(userService.getCurrentUserDto(), HttpStatus.OK);
    }


}
