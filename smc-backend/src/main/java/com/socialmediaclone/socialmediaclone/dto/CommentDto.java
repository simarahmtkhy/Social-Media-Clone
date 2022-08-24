package com.socialmediaclone.socialmediaclone.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private Long commentId;
    private String user;
    private String text;
    private Long post;
}
