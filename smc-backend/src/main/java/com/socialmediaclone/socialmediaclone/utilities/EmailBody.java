package com.socialmediaclone.socialmediaclone.utilities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmailBody {

    private String recipient;
    private String msgBody;
    private String subject;
}
