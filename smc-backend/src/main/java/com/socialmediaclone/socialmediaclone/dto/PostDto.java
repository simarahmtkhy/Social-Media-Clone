package com.socialmediaclone.socialmediaclone.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PostDto {

    private Long id;
    private String username;
    private String date;
    private List<CommentDto> comments;
    private Set<UserDto> likes;
    private ContentBodyDto body;
}
