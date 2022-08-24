package com.socialmediaclone.socialmediaclone.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowRequestDto {
    private Long id;
    private String senderName;
    private String receiverName;
}
