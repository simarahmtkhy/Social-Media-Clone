package com.socialmediaclone.socialmediaclone.entities;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Post {
    @Id
    @SequenceGenerator(name = "post_id_seq", sequenceName = "post_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "post_id_seq", strategy = GenerationType.SEQUENCE)
    private Long postId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;
    private LocalDateTime dateTime;
    @OneToOne(cascade = CascadeType.ALL)
    private ContentBody body;
    @OneToMany(orphanRemoval = true, mappedBy = "post")
    private List<Comment> comments = new LinkedList<Comment>();
    @ManyToMany
    private Set<User> likes = new HashSet<User>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void addLikes(User user) {
        this.likes.add(user);
    }

    public void removeLike(User user){
        this.likes.remove(user);
    }
    public void removeComment(Comment comment){
        this.comments.remove(comment);
    }

    public Set<User> getLikes() {
        return this.likes;
    }

    public Long getPostId() {
        return postId;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public ContentBody getBody() {
        return body;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
