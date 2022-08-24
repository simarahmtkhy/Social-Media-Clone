package com.socialmediaclone.socialmediaclone.services;

import com.socialmediaclone.socialmediaclone.dto.*;
import com.socialmediaclone.socialmediaclone.entities.*;
import com.socialmediaclone.socialmediaclone.exceptions.*;
import com.socialmediaclone.socialmediaclone.mapper.DtoMapper;
import com.socialmediaclone.socialmediaclone.respositories.UserRepository;
import com.socialmediaclone.socialmediaclone.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final ImageService imageService;

    private final PostService postService;

    private final MessageService messageService;

    private final VideoService videoService;

    private final CommentService commentService;

    private final PasswordEncoder passwordEncoder;

    private final FollowService followService;

    @Autowired
    public UserService(UserRepository userRepository, ImageService imageService,
                       PostService postService, MessageService messageService,
                       VideoService videoService, CommentService commentService, PasswordEncoder passwordEncoder, FollowService followService) {
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.postService = postService;
        this.messageService = messageService;
        this.videoService = videoService;
        this.commentService = commentService;
        this.passwordEncoder = passwordEncoder;
        this.followService = followService;
    }

    //=========================================================
    // methods in correspondence to the methods in the controller layer

    public void addUser(User user) {
        checkUserInformation(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addRole(Roles.ROLE_USER.toString());
        user.setVerificationToken(UUID.randomUUID().toString());
        this.userRepository.save(user);
    }

    public PostDto userPosts(Post post) {
        if (post.getBody().getText().isEmpty()) {
            throw new PostNotFoundException("Post body cannot be empty");
        }
        User currentUser = getCurrentUser();
        this.postService.savePost(currentUser, post);       // save the post
        currentUser.addPost(post);
        this.userRepository.save(currentUser);              // save the user again as a field of the user has been updated
        return DtoMapper.INSTANCE.postToPostDto(post);
    }

    public Long uploadFile(MultipartFile file) throws IOException {
        if (Objects.requireNonNull(file.getContentType()).startsWith("image")) {
            return uploadImage(file);
        } else if (Objects.requireNonNull(file.getContentType()).startsWith("video")) {
            return uploadVideo(file);
        } else {
            throw new FileNotCompatible("File type Not Supported");
        }
    }

    public Long uploadImage(MultipartFile file) throws IOException {
        return this.imageService.save(file);
    }

    public Long uploadVideo(MultipartFile file) throws IOException {
        return this.videoService.save(file);
    }

    public Post getPost(Long postId) {
        securityCheck(postId);
        return this.postService.getPost(postId);
    }

    public PostDto getPostDto(Long postId) {
        securityCheck(postId);
        return this.postService.getPostDto(postId);
    }

    public void follow(String username) {
        User currentUser = getCurrentUser();
        User user = this.getUser(username);
        if (Objects.equals(currentUser.getUserId(), user.getUserId())) {
            throw new UserHasNoAuthorizationException("User can not follow himself");
        }
        currentUser.addFollowing(user);
        user.addFollower(currentUser);
        this.userRepository.save(currentUser);
//        this.userRepository.save(userToBeFollowed);
    }

    public FollowRequestDto followRequest(String username) {
        User currentUser = getCurrentUser();
        User user = this.getUser(username);
        if (Objects.equals(currentUser.getUserId(), user.getUserId())) {
            throw new UserHasNoAuthorizationException("User can not follow himself");
        }
        return followService.followRequest(currentUser, user);
    }

    public void acceptRequest(Long reqId) {
        FollowRequest request = followService.getFollowRequest(reqId);
        User user = request.getSender();
        User currentUser = request.getReceiver();
        if (!Objects.equals(getCurrentUser(), currentUser)) {
            throw new UserHasNoAuthorizationException("No auth");
        } else {
            user.addFollowing(currentUser);
            currentUser.addFollower(user);
            userRepository.save(currentUser);
            followService.delete(request.getId());
        }
    }

    public void rejectRequest(Long reqId) {
        followService.delete(reqId);
    }

    public List<FollowRequestDto> getRequests() {
        return followService.getRequests(getCurrentUser());
    }

    public void cancelReq(Long reqId) {
        followService.delete(reqId);
    }

    public void unfollow(String username) {
        User currentUser = getCurrentUser();
        User user = getUser(username);
        if (!isFollower(currentUser.getUserId(), user.getUserId())) {
            throw new UserNotFollowerException("Already Not Following The User");
        } else {
            currentUser.removeFollowing(user);
            user.removeFollower(currentUser);
            this.userRepository.save(currentUser);
        }
    }

    public void like(Long postId) {
        securityCheck(postId);
        Post post = postService.getPost(postId);
        User currentUser = getCurrentUser();
        post.addLikes(currentUser);
        this.userRepository.save(currentUser);
    }

    public CommentDto comment(Long postId, Comment comment) {
        if (comment.getText().isEmpty()) {
            throw new CommentNotFoundException("Comment Is Empty");
        }
        securityCheck(postId);
        User currentUser = getCurrentUser();
        Post currentPost = this.postService.getPost(postId);
        comment.setUser(currentUser);
        comment.setPost(currentPost);
        currentPost.addComment(comment);
        this.userRepository.save(currentUser);
        this.commentService.save(comment);
        return DtoMapper.INSTANCE.commentToCommentDto(comment);
    }

    public void rlike(Long postId) {
        securityCheck(postId);
        User currentUser = getCurrentUser();
        Post currentPost = this.postService.getPost(postId);
        if (currentPost.getLikes().contains(currentUser)) {
            currentPost.removeLike(currentUser);
            this.userRepository.save(currentUser);
        } else {
            throw new UserHasNoAuthorizationException("User Did Not Like The Post");
        }

    }

    public List<CommentDto> rcomment(Long postId, Long comment_id) {
        securityCheck(postId);
        User currentUser = getCurrentUser();
        Post currentPost = this.postService.getPost(postId);
        Comment currentComment = this.commentService.getComment(comment_id);
        if (!Objects.equals(currentComment.getUser().getUserId(), currentUser.getUserId())) {
            throw new UserHasNoAuthorizationException("User Is Not The Author Of The Comment");
        } else {
            currentPost.getComments().remove(currentComment);
            return commentService.delete(currentComment, currentPost);
        }
    }

    public List<PostDto> getPosts(String username, Integer pageNumber, Integer pageSize) {
        User currentUser = getCurrentUser();
        User user = getUser(username);
        if (!isFollower(currentUser.getUserId(), user.getUserId())) {
            throw new UserNotFollowerException("User Is Not A Follower");
        } else {
            return this.postService.getPosts(user, pageNumber, pageSize);
        }
    }

    public List<PostDto> getPosts(Integer pageNumber, Integer pageSize) {
        User currentUser = getCurrentUser();
        return this.postService.getPosts(currentUser, pageNumber, pageSize);
    }

    public MessageDto sendMessage(String username, Message message) {
        User currentUser = getCurrentUser();
        User user = getUser(username);
        if (!isFollower(currentUser.getUserId(), user.getUserId())) {
            throw new UserNotFollowerException("User Not A Follower");
        } else {
            addChat(user);
            return this.messageService.saveMessage(message, currentUser, user, LocalDateTime.now());
        }
    }

    public List<MessageDto> getMessages(String username, Integer pageNumber, Integer pageSize) {
        return this.messageService.findAllMessages(getCurrentUser(), getUser(username), pageNumber, pageSize);
    }

    public List<PostDto> getMainFeed(Integer pageNumber, Integer pageSize) {
        Set<User> following = getFollowing(getCurrentUser().getUserId());
        return postService.getPostsForMainFeed(following, pageNumber, pageSize);
    }

    public boolean isFollowerCheck(String username) {
        User current = getCurrentUser();
        User other = getUser(username);
        return isFollower(current.getUserId(), other.getUserId());
    }

    public boolean hasPendingRequest(String username) {
        return followService.hasPendingRequestTo(getUser(username), getCurrentUser());
    }

    public List<UserDto> search(String search) {
        List<User> users = this.userRepository.searchUser(search, getCurrentUser().getUsername());
        return users.stream().map(user -> DtoMapper.INSTANCE.userToUserDto(user)
        ).collect(Collectors.toList());
    }

    public PostDto deletePost(Long postId) throws IOException {
        if (isTheUser(postId)) {
            return this.postService.deletePost(postId);
        } else {
            throw new UserHasNoAuthorizationException("User has no authorization");
        }
    }

    public List<CommentDto> getComments(Long postId) {
        securityCheck(postId);
        return postService.getComments(postId);
    }


    //=========================================================
    // helper methods

    public User getUser(Long user_id) {
        if (this.userRepository.findById(user_id).isPresent()) {
            return this.userRepository.findById(user_id).get();
        } else {
            throw new UserNotFoundException("User Not Found");
        }
    }

    public User getUser(String username) {
        return this.userRepository.findByUsername(username).get();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            if (userRepository.findByUsername(currentUserName).isEmpty()) {
                throw new UserNotFoundException("User Not Found");
            }
            return userRepository.findByUsername(currentUserName).get();
        } else {
            throw new UserNotFoundException("User Not Found");
        }
    }

    public Set<User> getFollowers(Long user_id) {
        if (this.userRepository.findById(user_id).isPresent()) {
            return this.userRepository.findById(user_id).get().getFollowers();
        } else {
            throw new UserNotFoundException("User Not Found");
        }
    }

    public Set<User> getFollowing(Long user_id) {
        if (this.userRepository.findById(user_id).isPresent()) {
            return this.userRepository.findById(user_id).get().getFollowing();
        } else {
            throw new UserNotFoundException("User Not Found");
        }
    }

    public boolean isFollower(Long user_id, Long other_user_id) {
        return this.getFollowers(other_user_id).contains(this.getUser(user_id));
    }

    public void securityCheck(Long postId) {
        User currentUser = getCurrentUser();
        Post post = postService.getPost(postId);
        if (!isFollower(currentUser.getUserId(), post.getUser().getUserId())) {
            throw new UserNotFollowerException("User not a follower");
        }
    }

    public void checkUserInformation(User user) {
        String email = user.getEmail();
        String userName = user.getUsername();
        if (userRepository.findByUsername(userName).isPresent()) {
            throw new UserNameTakenException("UserName Taken");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailTakenException("Email Taken");
        }

    }

    public void verifyUser(String token) {
        if (userRepository.findByVerificationToken(token).isEmpty()) {
            throw new UserNotFoundException("User Not Found");
        } else {
            User user = userRepository.findByVerificationToken(token).get();
            user.setVerified(true);
            userRepository.save(user);
        }
    }

    public boolean isTheUser(Long postId) {
        return Objects.equals(getCurrentUser().getUserId(), postService.getPost(postId).getUser().getUserId());
    }

    public boolean hasLiked(Long post_id) {
        return getPost(post_id).getLikes().contains(getCurrentUser());
    }

    public void addChat(User user) {
        getCurrentUser().addChat(user);
    }


    public UserDto getCurrentUserDto() {
        return DtoMapper.INSTANCE.userToUserDto(getCurrentUser());
    }



}



