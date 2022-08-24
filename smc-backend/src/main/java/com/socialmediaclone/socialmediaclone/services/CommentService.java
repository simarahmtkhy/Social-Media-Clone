package com.socialmediaclone.socialmediaclone.services;

import com.socialmediaclone.socialmediaclone.dto.CommentDto;
import com.socialmediaclone.socialmediaclone.entities.Comment;
import com.socialmediaclone.socialmediaclone.entities.Post;
import com.socialmediaclone.socialmediaclone.exceptions.CommentNotFoundException;
import com.socialmediaclone.socialmediaclone.mapper.DtoMapper;
import com.socialmediaclone.socialmediaclone.respositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    public Comment getComment(Long comment_id) {
        if (this.commentRepository.findById(comment_id).isEmpty()){
            throw new CommentNotFoundException("Comment Not Found");
        }
        return this.commentRepository.findById(comment_id).get();
    }

    public void save(Comment comment) {
        this.commentRepository.save(comment);
    }

    public List<CommentDto> delete(Comment currentComment, Post post) {
        this.commentRepository.delete(currentComment);
        return getComments(post);
    }

    public List<CommentDto> getComments(Post post) {
        List<Comment> comments = commentRepository.findAllByPost(post);
        return comments.stream().map(comment -> DtoMapper.INSTANCE.commentToCommentDto(comment)).collect(Collectors.toList());
    }
}
