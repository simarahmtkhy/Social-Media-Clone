package com.socialmediaclone.socialmediaclone.respositories;

import com.socialmediaclone.socialmediaclone.entities.FollowRequest;
import com.socialmediaclone.socialmediaclone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRequestRepository extends JpaRepository<FollowRequest, Long> {

    List<FollowRequest> findAllByReceiver(User user);

    Optional<FollowRequest> findByReceiverAndSender(User receiver, User sender);

    List<FollowRequest> findAllBySender(User user);
}
