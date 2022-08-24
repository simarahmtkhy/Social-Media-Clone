package com.socialmediaclone.socialmediaclone.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentBodyDto {
    private Long contentId;
    private String text;
    private String image;
    private String video;
}
