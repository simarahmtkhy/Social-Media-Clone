package com.socialmediaclone.socialmediaclone.services;

import com.socialmediaclone.socialmediaclone.dto.CommentDto;
import com.socialmediaclone.socialmediaclone.dto.PostDto;
import com.socialmediaclone.socialmediaclone.entities.Post;
import com.socialmediaclone.socialmediaclone.entities.User;
import com.socialmediaclone.socialmediaclone.exceptions.PostNotFoundException;
import com.socialmediaclone.socialmediaclone.mapper.DtoMapper;
import com.socialmediaclone.socialmediaclone.respositories.PostRepository;
import com.socialmediaclone.socialmediaclone.utilities.PostPage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final CommentService commentService;

    private final PostPage postPage;

    private final ImageService imageService;

    private final VideoService videoService;


    private DtoMapper mapper;

    public PostService(PostRepository postRepository, CommentService commentService, PostPage postPage, ImageService imageService, VideoService videoService) {
        this.postRepository = postRepository;
        this.commentService = commentService;
        this.postPage = postPage;
        this.imageService = imageService;
        this.videoService = videoService;
    }


    public Post getPost(Long post_id) {
        if (this.postRepository.findById(post_id).isEmpty()){
            throw new PostNotFoundException("Post Not Found");
        }
        return this.postRepository.findById(post_id).get();
    }

    public PostDto getPostDto(Long post_id) {
        if (this.postRepository.findById(post_id).isEmpty()){
            throw new PostNotFoundException("Post Not Found");
        }
        return DtoMapper.INSTANCE.postToPostDto(this.postRepository.findById(post_id).get());
    }

    public void savePost(User currentUser, Post post) {
        post.setUser(currentUser);
        post.setDateTime(LocalDateTime.now());
        this.postRepository.save(post);
    }

    public List<PostDto> getPostsForMainFeed(Set<User> users, Integer pageNumber, Integer pageSize){
        if (Objects.nonNull(pageNumber)){
            postPage.setPageNumber(pageNumber);
        }
        if (Objects.nonNull(pageSize)){
            if (pageSize < postPage.getMaxPageSize()){
                postPage.setPageSize(pageSize);
            }
            else {
                postPage.setPageSize(postPage.getMaxPageSize());
            }
        }
        Sort sort = Sort.by(postPage.getSortDirection(), postPage.getSortBy());
        Pageable pageable = PageRequest.of(postPage.getPageNumber(), postPage.getPageSize(), sort);
        List<Post> posts = this.postRepository.getPostByUserIn(users, pageable);
        postPage.setBeanDefault();
        return posts.stream().map(post -> mapper.INSTANCE.postToPostDto(post)).collect(Collectors.toList());
    }

    public List<PostDto> getPosts(User user, Integer pageNumber, Integer pageSize) {
        if (Objects.nonNull(pageNumber)){
            postPage.setPageNumber(pageNumber);
        }
        if (Objects.nonNull(pageSize)){
            if (pageSize < postPage.getMaxPageSize()){
                postPage.setPageSize(pageSize);
            }
            else {
                postPage.setPageSize(postPage.getMaxPageSize());
            }
        }
        Sort sort = Sort.by(postPage.getSortDirection(), postPage.getSortBy());
        Pageable pageable = PageRequest.of(postPage.getPageNumber(), postPage.getPageSize(), sort);
        List<Post> posts = this.postRepository.getPostByUser(user, pageable);
        return posts.stream().map(post -> mapper.INSTANCE.postToPostDto(post)).collect(Collectors.toList());
    }

    public PostDto deletePost(Long postId) throws IOException {
        PostDto postDto = DtoMapper.INSTANCE.postToPostDto(getPost(postId));
        if (getPost(postId).getBody().getImage() != null) {
            imageService.deleteImage(getPost(postId).getBody().getImage().getPath());
        }
        else if (getPost(postId).getBody().getVideo() != null){
            videoService.deleteVideo(getPost(postId).getBody().getVideo().getPath());
        }
        postRepository.deleteById(postId);
        return postDto;
    }

    public List<CommentDto> getComments(Long postId) {
        return commentService.getComments(getPost(postId));
    }
}
