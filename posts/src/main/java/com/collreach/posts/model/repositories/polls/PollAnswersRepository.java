package com.collreach.posts.model.repositories.polls;

import com.collreach.posts.model.bo.polls.PollAnswers;
import com.collreach.posts.model.bo.polls.Polls;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PollAnswersRepository extends CrudRepository<PollAnswers, Integer> {
    public List<PollAnswers> findAllByPollId(Polls pollId);
}
