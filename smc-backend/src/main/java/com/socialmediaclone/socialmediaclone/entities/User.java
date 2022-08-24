package com.socialmediaclone.socialmediaclone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_id_seq", strategy = GenerationType.SEQUENCE)
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String verificationToken;
    private boolean isVerified = false;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new LinkedList<Post>();
    @JsonIgnore
    @ManyToMany
    private Set<User> followers = new HashSet<User>();
    @JsonIgnore
    @ManyToMany
    private Set<User> following = new HashSet<User>();

    @JsonIgnore
    @ManyToMany
    private Set<User> chats = new HashSet<User>();


    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void addFollower(User user) {
        this.followers.add(user);
    }

    public void removeFollower(User user){
        this.followers.remove(user);
    }

    public void addFollowing(User user) {
        this.following.add(user);
    }

    public void removeFollowing(User user){
        this.following.remove(user);
    }


    public void addRole(String role) {
        roles.add(role);
    }

    public void addChat(User user) {
        this.chats.add(user);
    }

}
