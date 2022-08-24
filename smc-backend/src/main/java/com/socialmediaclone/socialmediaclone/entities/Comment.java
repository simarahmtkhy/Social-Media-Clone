package com.socialmediaclone.socialmediaclone.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {

    @Id
    @SequenceGenerator(name = "comment_id_seq", sequenceName = "comment_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "comment_id_seq", strategy = GenerationType.SEQUENCE)
    private Long commentId;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    private String text;
    @ManyToOne
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;


}
