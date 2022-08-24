package com.socialmediaclone.socialmediaclone.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionObject> handleUserNotAFollowerException(UserNotFollowerException e){
        ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), HttpStatus.FORBIDDEN, LocalDateTime.now());
        return new ResponseEntity<>(exceptionObject, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionObject> handleUserNotFoundException(UserNotFoundException e){
        ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(exceptionObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionObject> handleUserHasNoAuthorizationException(UserHasNoAuthorizationException e){
        ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), HttpStatus.FORBIDDEN, LocalDateTime.now());
        return new ResponseEntity<>(exceptionObject, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionObject> handlePostNotFoundException(PostNotFoundException e){
        ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(exceptionObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionObject> handleCommentNotFoundException(CommentNotFoundException e){
        ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionObject> handleUserNameTakenException(UserNameTakenException e){
        ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), HttpStatus.CONFLICT, LocalDateTime.now());
        return new ResponseEntity<>(exceptionObject, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionObject> handleEmailTakenException(EmailTakenException e) {
        ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), HttpStatus.CONFLICT, LocalDateTime.now());
        return new ResponseEntity<>(exceptionObject, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionObject> handleRefreshException(RefreshTokenException e) {
        ExceptionObject exceptionObject = new ExceptionObject(e.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(exceptionObject, HttpStatus.BAD_REQUEST);
    }
}
