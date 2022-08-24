package com.socialmediaclone.socialmediaclone.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {
    private Long id;
    private String senderName;
    private String receiverName;
    private ContentBodyDto content;
    private String dateTime;
}
