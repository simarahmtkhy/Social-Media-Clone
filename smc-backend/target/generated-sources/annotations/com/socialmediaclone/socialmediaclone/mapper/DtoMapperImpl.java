package com.socialmediaclone.socialmediaclone.mapper;

import com.socialmediaclone.socialmediaclone.dto.CommentDto;
import com.socialmediaclone.socialmediaclone.dto.ContentBodyDto;
import com.socialmediaclone.socialmediaclone.dto.FollowRequestDto;
import com.socialmediaclone.socialmediaclone.dto.ImageDto;
import com.socialmediaclone.socialmediaclone.dto.MessageDto;
import com.socialmediaclone.socialmediaclone.dto.PostDto;
import com.socialmediaclone.socialmediaclone.dto.UserDto;
import com.socialmediaclone.socialmediaclone.dto.VideoDto;
import com.socialmediaclone.socialmediaclone.entities.Comment;
import com.socialmediaclone.socialmediaclone.entities.ContentBody;
import com.socialmediaclone.socialmediaclone.entities.FollowRequest;
import com.socialmediaclone.socialmediaclone.entities.Image;
import com.socialmediaclone.socialmediaclone.entities.Message;
import com.socialmediaclone.socialmediaclone.entities.Post;
import com.socialmediaclone.socialmediaclone.entities.User;
import com.socialmediaclone.socialmediaclone.entities.Video;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-15T15:10:15+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.1.1 (Oracle Corporation)"
)
public class DtoMapperImpl implements DtoMapper {

    @Override
    public PostDto postToPostDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDto postDto = new PostDto();

        postDto.setId( post.getPostId() );
        postDto.setUsername( postUserUsername( post ) );
        if ( post.getDateTime() != null ) {
            postDto.setDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( post.getDateTime() ) );
        }
        postDto.setComments( commentListToCommentDtoList( post.getComments() ) );
        postDto.setLikes( userSetToUserDtoSet( post.getLikes() ) );
        postDto.setBody( bodyToBodyDto( post.getBody() ) );

        return postDto;
    }

    @Override
    public CommentDto commentToCommentDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDto commentDto = new CommentDto();

        commentDto.setCommentId( comment.getCommentId() );
        commentDto.setUser( commentUserUsername( comment ) );
        commentDto.setText( comment.getText() );
        commentDto.setPost( commentPostPostId( comment ) );

        return commentDto;
    }

    @Override
    public UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setUserId( user.getUserId() );
        userDto.setUsername( user.getUsername() );
        userDto.setEmail( user.getEmail() );

        return userDto;
    }

    @Override
    public MessageDto messageToMessageDto(Message message) {
        if ( message == null ) {
            return null;
        }

        MessageDto messageDto = new MessageDto();

        messageDto.setId( message.getMessageId() );
        messageDto.setSenderName( messageSenderUsername( message ) );
        messageDto.setReceiverName( messageReceiverUsername( message ) );
        messageDto.setContent( bodyToBodyDto( message.getBody() ) );
        if ( message.getDateTime() != null ) {
            messageDto.setDateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( message.getDateTime() ) );
        }

        return messageDto;
    }

    @Override
    public FollowRequestDto reqToReqDto(FollowRequest request) {
        if ( request == null ) {
            return null;
        }

        FollowRequestDto followRequestDto = new FollowRequestDto();

        followRequestDto.setSenderName( requestSenderUsername( request ) );
        followRequestDto.setReceiverName( requestReceiverUsername( request ) );
        followRequestDto.setId( request.getId() );

        return followRequestDto;
    }

    @Override
    public ContentBodyDto bodyToBodyDto(ContentBody contentBody) {
        if ( contentBody == null ) {
            return null;
        }

        ContentBodyDto contentBodyDto = new ContentBodyDto();

        contentBodyDto.setContentId( contentBody.getContentId() );
        contentBodyDto.setText( contentBody.getText() );
        contentBodyDto.setImage( contentBodyImageUuid( contentBody ) );
        contentBodyDto.setVideo( contentBodyVideoUuid( contentBody ) );

        return contentBodyDto;
    }

    @Override
    public ImageDto imageToImageDto(Image image) {
        if ( image == null ) {
            return null;
        }

        ImageDto imageDto = new ImageDto();

        imageDto.setId( image.getId() );
        imageDto.setUuid( image.getUuid() );

        return imageDto;
    }

    @Override
    public VideoDto videoToVideoDto(Video video) {
        if ( video == null ) {
            return null;
        }

        VideoDto videoDto = new VideoDto();

        videoDto.setId( video.getId() );
        videoDto.setUuid( video.getUuid() );

        return videoDto;
    }

    private String postUserUsername(Post post) {
        if ( post == null ) {
            return null;
        }
        User user = post.getUser();
        if ( user == null ) {
            return null;
        }
        String username = user.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    protected List<CommentDto> commentListToCommentDtoList(List<Comment> list) {
        if ( list == null ) {
            return null;
        }

        List<CommentDto> list1 = new ArrayList<CommentDto>( list.size() );
        for ( Comment comment : list ) {
            list1.add( commentToCommentDto( comment ) );
        }

        return list1;
    }

    protected Set<UserDto> userSetToUserDtoSet(Set<User> set) {
        if ( set == null ) {
            return null;
        }

        Set<UserDto> set1 = new LinkedHashSet<UserDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( User user : set ) {
            set1.add( userToUserDto( user ) );
        }

        return set1;
    }

    private String commentUserUsername(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        User user = comment.getUser();
        if ( user == null ) {
            return null;
        }
        String username = user.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    private Long commentPostPostId(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        Post post = comment.getPost();
        if ( post == null ) {
            return null;
        }
        Long postId = post.getPostId();
        if ( postId == null ) {
            return null;
        }
        return postId;
    }

    private String messageSenderUsername(Message message) {
        if ( message == null ) {
            return null;
        }
        User sender = message.getSender();
        if ( sender == null ) {
            return null;
        }
        String username = sender.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    private String messageReceiverUsername(Message message) {
        if ( message == null ) {
            return null;
        }
        User receiver = message.getReceiver();
        if ( receiver == null ) {
            return null;
        }
        String username = receiver.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    private String requestSenderUsername(FollowRequest followRequest) {
        if ( followRequest == null ) {
            return null;
        }
        User sender = followRequest.getSender();
        if ( sender == null ) {
            return null;
        }
        String username = sender.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    private String requestReceiverUsername(FollowRequest followRequest) {
        if ( followRequest == null ) {
            return null;
        }
        User receiver = followRequest.getReceiver();
        if ( receiver == null ) {
            return null;
        }
        String username = receiver.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    private String contentBodyImageUuid(ContentBody contentBody) {
        if ( contentBody == null ) {
            return null;
        }
        Image image = contentBody.getImage();
        if ( image == null ) {
            return null;
        }
        String uuid = image.getUuid();
        if ( uuid == null ) {
            return null;
        }
        return uuid;
    }

    private String contentBodyVideoUuid(ContentBody contentBody) {
        if ( contentBody == null ) {
            return null;
        }
        Video video = contentBody.getVideo();
        if ( video == null ) {
            return null;
        }
        String uuid = video.getUuid();
        if ( uuid == null ) {
            return null;
        }
        return uuid;
    }
}
