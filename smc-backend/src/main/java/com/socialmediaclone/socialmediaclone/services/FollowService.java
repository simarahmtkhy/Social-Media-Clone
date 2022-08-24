package com.socialmediaclone.socialmediaclone.services;

import com.socialmediaclone.socialmediaclone.dto.FollowRequestDto;
import com.socialmediaclone.socialmediaclone.entities.FollowRequest;
import com.socialmediaclone.socialmediaclone.entities.User;
import com.socialmediaclone.socialmediaclone.exceptions.UserNotFoundException;
import com.socialmediaclone.socialmediaclone.mapper.DtoMapper;
import com.socialmediaclone.socialmediaclone.respositories.FollowRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowService {

    private final FollowRequestRepository requestRepository;

    public FollowService(FollowRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public FollowRequestDto followRequest(User currentUser, User user) {
        FollowRequest request = new FollowRequest(currentUser, user);
        if (requestRepository.findByReceiverAndSender(user, currentUser).isEmpty()){
            requestRepository.save(request);
            return DtoMapper.INSTANCE.reqToReqDto(request);
        }
        else {
            FollowRequest followRequest = requestRepository.findByReceiverAndSender(user, currentUser).get();
            return DtoMapper.INSTANCE.reqToReqDto(followRequest);
        }

    }


    public FollowRequest getFollowRequest(Long id) {
        if (requestRepository.findById(id).isPresent()) {
            return requestRepository.findById(id).get();
        }
        else {
            throw new UserNotFoundException("User has not found");
        }

    }

    public List<FollowRequest> getRequestsBySender(User user) {
        return requestRepository.findAllBySender(user);
    }

    public void delete(Long requestId) {
        requestRepository.deleteById(requestId);
    }

    public List<FollowRequestDto> getRequests(User user) {
        List<FollowRequest> requests = requestRepository.findAllByReceiver(user);
        return requests.stream().map(request -> DtoMapper.INSTANCE.reqToReqDto(request)).collect(Collectors.toList());
    }

    public boolean hasPendingRequestTo(User user, User currentUser) {
        for (FollowRequest request: getRequestsBySender(currentUser)) {
            if (request.getReceiver() == user) {
                return true;
            }
        }
        return false;

    }
}
