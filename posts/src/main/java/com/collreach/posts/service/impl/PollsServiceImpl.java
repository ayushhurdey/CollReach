package com.collreach.posts.service.impl;

import com.collreach.posts.model.bo.Users;
import com.collreach.posts.model.bo.polls.PollAnswers;
import com.collreach.posts.model.bo.polls.Polls;
import com.collreach.posts.model.repositories.UsersRepository;
import com.collreach.posts.model.repositories.polls.AnswersRepository;
import com.collreach.posts.model.repositories.polls.PollsRepository;
import com.collreach.posts.model.requests.CreatePollRequest;
import com.collreach.posts.model.response.MessageResponse;
import com.collreach.posts.model.response.PollAnswersResponse;
import com.collreach.posts.responses.ResponseMessage;
import com.collreach.posts.service.PollsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

@Service
public class PollsServiceImpl implements PollsService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PollsRepository pollsRepository;

    @Autowired
    AnswersRepository answersRepository;

    @Override
    @Transactional
    public String createPoll(CreatePollRequest createPollRequest) {
        Optional<Users> user = usersRepository.findByUserName(createPollRequest.getUserName());
        if(user.isPresent()){
            try {
                Polls polls = new Polls();
                polls.setUserId(user.get());
                polls.setQuestion(createPollRequest.getQuestion());
                polls.setDateCreated(createPollRequest.getDateCreated());
                polls.setTimeCreated(createPollRequest.getTimeCreated());
                polls.setRecurrences(createPollRequest.getRecurrences());
                polls.setValidityInWeeks(createPollRequest.getValidityInWeeks());
                polls.setVisibility(createPollRequest.getVisibility());

                Polls savedPoll = pollsRepository.save(polls);

                for(String answer: createPollRequest.getAnswers()){
                    PollAnswers pollAnswers = new PollAnswers();
                    pollAnswers.setPollId(savedPoll);
                    pollAnswers.setAnswer(answer);
                    answersRepository.save(pollAnswers);
                }
                return ResponseMessage.POLL_CREATED;
            }catch(Exception e){
                System.out.println("Some error occurred in creating poll.");
                return ResponseMessage.PROCESSING_ERROR;
            }
        }
        return ResponseMessage.USER_NOT_FOUND;
    }

    @Override
    public LinkedHashSet<MessageResponse> getPolls(Integer pageNo, Integer pageSize, String visibility) {
        LinkedHashSet<MessageResponse> set = new LinkedHashSet<>();
        Sort dateSort = Sort.by("dateCreated").descending();
        Sort timeSort = Sort.by("timeCreated").descending();
        Sort groupBySort = dateSort.and(timeSort);
        Pageable paging = PageRequest.of(pageNo, pageSize, groupBySort);

        pollsRepository.findAllByVisibilityOrVisibility(visibility,"college", paging)
                .forEach(poll -> {
                    MessageResponse messageResponse = new MessageResponse();
                    messageResponse.setQuestion(poll.getQuestion());
                    messageResponse.setLifetimeInWeeks(poll.getValidityInWeeks());
                    messageResponse.setCreateDate(poll.getDateCreated());
                    messageResponse.setUploadTime(poll.getTimeCreated());
                    messageResponse.setVisibility(poll.getVisibility());
                    messageResponse.setRecurrences(poll.getRecurrences());
                    List<PollAnswersResponse> pollAnswers = new ArrayList<>();
                    answersRepository.findAllByPollId(poll).forEach(answer -> {
                        PollAnswersResponse pollAnswersResponse = new PollAnswersResponse(
                                answer.getAnswerId(), answer.getAnswer(), answer.getVotes()
                        );
                        pollAnswers.add(pollAnswersResponse);
                    });
                    messageResponse.setAnswers(pollAnswers);
                    set.add(messageResponse);
                });
        return set;
    }
}
