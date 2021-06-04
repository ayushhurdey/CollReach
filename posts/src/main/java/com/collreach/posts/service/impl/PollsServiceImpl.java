package com.collreach.posts.service.impl;

import com.collreach.posts.model.bo.Users;
import com.collreach.posts.model.bo.polls.PollAnswers;
import com.collreach.posts.model.bo.polls.Polls;
import com.collreach.posts.model.bo.polls.UserPollKey;
import com.collreach.posts.model.bo.polls.UsersPolled;
import com.collreach.posts.model.bo.posts.Messages;
import com.collreach.posts.model.repositories.UsersRepository;
import com.collreach.posts.model.repositories.polls.PollAnswersRepository;
import com.collreach.posts.model.repositories.polls.PollsRepository;
import com.collreach.posts.model.repositories.polls.UsersPolledRepository;
import com.collreach.posts.model.requests.CreatePollRequest;
import com.collreach.posts.model.response.*;
import com.collreach.posts.responses.ResponseMessage;
import com.collreach.posts.service.PollsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
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
    PollAnswersRepository pollAnswersRepository;

    @Autowired
    UsersPolledRepository usersPolledRepository;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

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
                polls.setTotalVotes(0);

                Polls savedPoll = pollsRepository.save(polls);

                for(String answer: createPollRequest.getAnswers()){
                    PollAnswers pollAnswers = new PollAnswers();
                    pollAnswers.setPollId(savedPoll);
                    pollAnswers.setAnswer(answer);
                    pollAnswersRepository.save(pollAnswers);
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
                    messageResponse.setPollId(poll.getPollId());
                    messageResponse.setQuestion(poll.getQuestion());
                    messageResponse.setLifetimeInWeeks(poll.getValidityInWeeks());
                    messageResponse.setCreateDate(poll.getDateCreated());
                    messageResponse.setUploadTime(poll.getTimeCreated());
                    messageResponse.setVisibility(poll.getVisibility());
                    messageResponse.setUsername(poll.getUserId().getUserName());
                    messageResponse.setRecurrences(poll.getRecurrences());
                    messageResponse.setName(poll.getUserId().getName());
                    messageResponse.setAnswers(getAnswersOfPoll(poll));
                    set.add(messageResponse);
                });
        return set;
    }

    private List<PollAnswersResponse> getAnswersOfPoll(Polls poll){
        List<PollAnswersResponse> pollAnswers = new ArrayList<>();
        pollAnswersRepository.findAllByPollId(poll).forEach(answer -> {
            int percentage = normalizePercentage(answer.getVotes(),  poll.getTotalVotes());
            PollAnswersResponse pollAnswersResponse = new PollAnswersResponse(
                    answer.getAnswerId(), answer.getAnswer(), answer.getVotes(), percentage
            );
            pollAnswers.add(pollAnswersResponse);
        });
        return pollAnswers;
    }

    @Override
    public UserPollsResponse updatesAnswersVotes(String userName, int pollId, int answerId) {
        Optional<Users> user = usersRepository.findByUserName(userName);
        if(user.isPresent()){
            Optional<Polls> poll = pollsRepository.findById(pollId);
            Optional<PollAnswers> pollAnswer = pollAnswersRepository.findById(answerId);
            Optional<UsersPolled> userAlreadyPolled = usersPolledRepository
                    .findById(new UserPollKey(user.get().getUserId(),pollId));

            if(poll.isPresent() && pollAnswer.isPresent() && !userAlreadyPolled.isPresent()){
                try {
                    int previousVotesOfAnswer = pollAnswer.get().getVotes();
                    long totalPreviousVotes = poll.get().getTotalVotes();
                    poll.get().setTotalVotes(totalPreviousVotes + 1);
                    pollAnswer.get().setVotes(previousVotesOfAnswer+1);
                    UsersPolled usersPolled = new UsersPolled();
                    usersPolled.setPollId(poll.get());
                    usersPolled.setUserId(user.get());
                    usersPolled.setIfVoted('T');
                    pollsRepository.save(poll.get());
                    usersPolledRepository.save(usersPolled);
                    pollAnswersRepository.save(pollAnswer.get());
                    simpMessagingTemplate.convertAndSend("/topic/votesUpdate", new LikesAndViewsUpdateResponse(
                            poll.get().getPollId(), poll.get().getTotalVotes()
                    ));
                    return getAllAnswersOfPoll(poll.get(), 0);
                }catch(Exception e){
                    return null;
                }
            }else{
                return null;
            }
        }
        return null;
    }

    @Override
    public UserPollsResponse getAllAnswersOfPoll(Polls pollId, int pollID){
        if(pollId == null){
            Optional<Polls> poll = pollsRepository.findById(pollID);
            pollId = poll.get();
        }

        long totalVotes = pollId.getTotalVotes();
        UserPollsResponse userPollsResponse = new UserPollsResponse();
        List<PollAnswers> pollAnswersList =  pollAnswersRepository.findAllByPollId(pollId);
        List<PollAnswersResponse> listOfPollAnswers =  new ArrayList<>();
        DecimalFormat df=new DecimalFormat("#.##");
        pollAnswersList.forEach(pollAnswer -> {
            int percentage = normalizePercentage(pollAnswer.getVotes(), totalVotes);
            listOfPollAnswers.add(
                    new PollAnswersResponse(
                            pollAnswer.getAnswerId(), pollAnswer.getAnswer(),pollAnswer.getVotes(), percentage
                    ));
        });
        userPollsResponse.setMessageId(pollId.getPollId());
        userPollsResponse.setTotalVotes(totalVotes);
        userPollsResponse.setAnswers(listOfPollAnswers);

        return userPollsResponse;
    }

    private int normalizePercentage(int votes, long totalVotes){
        float percent = ((float)votes / totalVotes * 100);
        int percentage = 0;
        if((percent - (int)percent) > 0.5){
            percentage = (int) Math.ceil(percent);
        }
        else{
            percentage = (int) Math.floor(percent);
        }
        return percentage;
    }

    @Override
    public MessagesResponse getPollsByUsername(String username,int pageNo, int pageSize){
        LinkedHashSet<MessageResponse> posts = new LinkedHashSet<>();
        Optional<Users> user = usersRepository.findByUserName(username);
        Sort dateSort = Sort.by("dateCreated").descending();
        Sort timeSort = Sort.by("timeCreated").descending();
        Sort groupBySort = dateSort.and(timeSort);
        Pageable paging = PageRequest.of(pageNo, pageSize, groupBySort);

        if(user.isPresent()){
            List<Polls> polls = pollsRepository.findAllByUserId(user.get(),paging);
            polls.forEach( poll -> {
                posts.add(new MessageResponse(poll.getPollId(), poll.getVisibility(),
                        poll.getUserId().getUserName(), poll.getValidityInWeeks(),
                        poll.getRecurrences(), poll.getUserId().getName(), poll.getTimeCreated(),
                        poll.getDateCreated(), poll.getQuestion(), getAnswersOfPoll(poll))
                );
            });
            return new MessagesResponse(posts);
        }
        return null;
    }

    @Override
    public String deletePoll(int pollId, String userName){
        Optional<Users> user = usersRepository.findByUserName(userName);
        Optional<Polls> poll = pollsRepository.findById(pollId);
        if(user.isPresent() &&
                poll.isPresent() &&
                user.get().getUserName()
                        .equals(poll.get().getUserId().getUserName())) {
//            usersPolledRepository.deleteAllByPollId(poll.get());
//            pollAnswersRepository.deleteAllByPollId(poll.get());
//            pollsRepository.deleteById(pollId);
            return ResponseMessage.SUCCESSFULLY_DONE;
        }
        return ResponseMessage.RECEIVED_INVALID_DATA;
    }
}
