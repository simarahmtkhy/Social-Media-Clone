package com.socialmediaclone.socialmediaclone.respositories;

import com.socialmediaclone.socialmediaclone.entities.Post;
import com.socialmediaclone.socialmediaclone.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> getPostByUserIn(Set<User> users, Pageable pageable);
    List<Post> getPostByUser(User user, Pageable pageable);
}
