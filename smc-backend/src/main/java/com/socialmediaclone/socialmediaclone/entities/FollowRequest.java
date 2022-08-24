package com.socialmediaclone.socialmediaclone.entities;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FollowRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private User sender;
    @OneToOne
    private User receiver;

    public FollowRequest(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
}
