package com.collreach.posts.service.impl;

import com.collreach.posts.model.bo.Users;
import com.collreach.posts.model.bo.polls.Answers;
import com.collreach.posts.model.bo.polls.Polls;
import com.collreach.posts.model.repositories.UsersRepository;
import com.collreach.posts.model.repositories.polls.AnswersRepository;
import com.collreach.posts.model.repositories.polls.PollsRepository;
import com.collreach.posts.model.requests.CreatePollRequest;
import com.collreach.posts.responses.ResponseMessage;
import com.collreach.posts.service.PollsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                    Answers answers = new Answers();
                    answers.setPoll_id(savedPoll);
                    answers.setAnswer(answer);
                    answersRepository.save(answers);
                }
                return ResponseMessage.POLL_CREATED;
            }catch(Exception e){
                System.out.println("Some error occurred in creating poll.");
                return ResponseMessage.PROCESSING_ERROR;
            }
        }
        return ResponseMessage.USER_NOT_FOUND;
    }
}
