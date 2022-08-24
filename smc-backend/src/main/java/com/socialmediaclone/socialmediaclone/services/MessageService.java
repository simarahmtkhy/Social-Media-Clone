package com.socialmediaclone.socialmediaclone.services;

import com.socialmediaclone.socialmediaclone.dto.MessageDto;
import com.socialmediaclone.socialmediaclone.entities.Message;
import com.socialmediaclone.socialmediaclone.entities.User;
import com.socialmediaclone.socialmediaclone.mapper.DtoMapper;
import com.socialmediaclone.socialmediaclone.respositories.MessageRepository;
import com.socialmediaclone.socialmediaclone.utilities.MessagePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    private final MessagePage messagePage;

    @Autowired
    public MessageService(MessageRepository messageRepository, MessagePage messagePage) {
        this.messageRepository = messageRepository;
        this.messagePage = messagePage;
    }
    
    public List<MessageDto> findAllMessages(User sender, User receiver, Integer pageNumber, Integer pageSize) {
        if (Objects.nonNull(pageNumber)){
            messagePage.setPageNumber(pageNumber);
        }
        if (Objects.nonNull(pageSize)){
            if (pageSize < messagePage.getMaxPageSize()){
                messagePage.setPageSize(pageSize);
            }
            else {
                messagePage.setPageSize(messagePage.getMaxPageSize());
            }
        }
        Sort sort = Sort.by(messagePage.getSortDirection(), messagePage.getSortBy());
        Pageable pageable = PageRequest.of(messagePage.getPageNumber(), messagePage.getPageSize(), sort);
        List<User> list = new LinkedList<>();
        list.add(sender);
        list.add(receiver);
        List<Message> messageList = messageRepository.findAllBySenderInAndReceiverIn(list, list, pageable);
        messagePage.setBeanDefault();
        return messageList.stream().map(message -> DtoMapper.INSTANCE.messageToMessageDto(message)).collect(Collectors.toList());
    }

    public MessageDto saveMessage(Message message, User sender, User receiver, LocalDateTime dateTime) {
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setDateTime(dateTime);
        this.messageRepository.save(message);
        return DtoMapper.INSTANCE.messageToMessageDto(message);
    }
}
