package com.socialmediaclone.socialmediaclone.respositories;

import com.socialmediaclone.socialmediaclone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.hibernate.loader.Loader.SELECT;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByVerificationToken(String uuid);

    @Query(value = "select user from User user where user.username like concat('%', :username, '%')" +
            " and user.username not like concat('%', :currentUser, '%')")
    List<User> searchUser(String username, String currentUser);
}
