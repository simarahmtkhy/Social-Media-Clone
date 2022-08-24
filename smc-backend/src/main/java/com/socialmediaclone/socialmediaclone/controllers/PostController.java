package com.socialmediaclone.socialmediaclone.controllers;

import com.socialmediaclone.socialmediaclone.dto.CommentDto;
import com.socialmediaclone.socialmediaclone.dto.PostDto;
import com.socialmediaclone.socialmediaclone.entities.Comment;
import com.socialmediaclone.socialmediaclone.entities.Post;
import com.socialmediaclone.socialmediaclone.services.PostService;
import com.socialmediaclone.socialmediaclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class PostController {


    private final UserService userService;

    @GetMapping(path = "/app/home")
    public ResponseEntity<List<PostDto>> getMainFeed(Integer pageNumber, Integer pageSize){
        return new ResponseEntity<>(this.userService.getMainFeed(pageNumber, pageSize), HttpStatus.OK);
    }
    @PostMapping(path = "/app/post")
    public ResponseEntity<PostDto> userPosts(@RequestBody Post post){
        return new ResponseEntity<>(this.userService.userPosts(post), HttpStatus.OK);
    }
    @PostMapping(path = "/app/upload")
    public ResponseEntity<Long> uploadFile(@RequestBody MultipartFile file) throws IOException {
        return new ResponseEntity<>(this.userService.uploadFile(file), HttpStatus.OK);
    }
    @GetMapping(path = "/app/posts/{post_id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long post_id){
        return new ResponseEntity<>(this.userService.getPostDto(post_id), HttpStatus.OK);
    }
    @GetMapping(path = "/app/users/{user_name}")
    public ResponseEntity<List<PostDto>> getProfile(@PathVariable String user_name, Integer pageNumber, Integer pageSize){
        return new ResponseEntity<>(this.userService.getPosts(user_name, pageNumber, pageSize), HttpStatus.OK);
    }
    @GetMapping(path = "/app/profile")
    public ResponseEntity<List<PostDto>> getMyProfile(Integer pageNumber, Integer pageSize){
        return new ResponseEntity<>(this.userService.getPosts(pageNumber, pageSize), HttpStatus.OK);
    }

    @DeleteMapping(path = "/app/posts/delete/{postId}")
    public ResponseEntity<PostDto> deletePost(@PathVariable Long postId) throws IOException {
        return new ResponseEntity<>(this.userService.deletePost(postId), HttpStatus.OK);
    }

    @GetMapping(path = "app/posts/comments/{postId}")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long postId) {
        return new ResponseEntity<>(userService.getComments(postId), HttpStatus.OK);
    }

    @PostMapping(path = "/app/posts/like/{post_id}")
    public ResponseEntity<Long> like(@PathVariable Long post_id){
        this.userService.like(post_id);
        return new ResponseEntity<>(post_id, HttpStatus.OK);
    }

    @GetMapping(path = "/app/posts/hasliked/{post_id}")
    public ResponseEntity<Boolean> hasLiked(@PathVariable Long post_id){
        return new ResponseEntity<>(this.userService.hasLiked(post_id), HttpStatus.OK);
    }

    @PostMapping(path = "/app/posts/comment/{post_id}")
    public ResponseEntity<CommentDto> comment(@PathVariable Long post_id, @RequestBody Comment comment){
        return new ResponseEntity<>(this.userService.comment(post_id, comment), HttpStatus.OK);
    }

    @DeleteMapping(path = "/app/posts/dislike/{post_id}")
    public ResponseEntity<Long> removeLike(@PathVariable Long post_id){
        this.userService.rlike(post_id);
        return new ResponseEntity<>(post_id, HttpStatus.OK);
    }

    @DeleteMapping(path = "/app/posts/deletecomment/{post_id}/{comment_id}")
    public ResponseEntity<List<CommentDto>> removeComment(@PathVariable Long post_id, @PathVariable Long comment_id){
        return new ResponseEntity<>(this.userService.rcomment(post_id, comment_id), HttpStatus.OK);
    }
}
