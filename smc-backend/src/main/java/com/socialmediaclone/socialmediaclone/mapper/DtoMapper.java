package com.socialmediaclone.socialmediaclone.mapper;

import com.socialmediaclone.socialmediaclone.dto.*;
import com.socialmediaclone.socialmediaclone.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DtoMapper {

    DtoMapper INSTANCE = Mappers.getMapper(DtoMapper.class);
    @Mapping(source = "postId", target = "id")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "dateTime", target = "date")
    @Mapping(source = "comments", target = "comments")
    @Mapping(source = "likes", target = "likes")
    @Mapping(source = "body", target = "body")
    PostDto postToPostDto(Post post);

    @Mapping(source = "commentId", target = "commentId")
    @Mapping(source = "user.username", target = "user")
    @Mapping(source = "text", target = "text")
    @Mapping(source = "post.postId", target = "post")
    CommentDto commentToCommentDto(Comment comment);

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    UserDto userToUserDto(User user);

    @Mapping(source = "messageId", target = "id")
    @Mapping(source = "sender.username", target = "senderName")
    @Mapping(source = "receiver.username", target = "receiverName")
    @Mapping(source = "body", target = "content")
    @Mapping(source = "dateTime", target = "dateTime")
    MessageDto messageToMessageDto(Message message);

    @Mapping(source = "sender.username", target = "senderName")
    @Mapping(source = "receiver.username", target = "receiverName")
    FollowRequestDto reqToReqDto(FollowRequest request);
    @Mapping(source = "contentId", target = "contentId")
    @Mapping(source = "text", target = "text")
    @Mapping(source = "image.uuid", target = "image")
    @Mapping(source = "video.uuid", target = "video")
    ContentBodyDto bodyToBodyDto(ContentBody contentBody);
    ImageDto imageToImageDto(Image image);
    VideoDto videoToVideoDto(Video video);

}
