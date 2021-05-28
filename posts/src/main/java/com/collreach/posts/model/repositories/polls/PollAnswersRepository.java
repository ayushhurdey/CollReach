package com.collreach.posts.model.repositories.polls;

import com.collreach.posts.model.bo.polls.PollAnswers;
import com.collreach.posts.model.bo.polls.Polls;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PollAnswersRepository extends CrudRepository<PollAnswers, Integer> {
    public List<PollAnswers> findAllByPollId(Polls pollId);

    @Modifying
    @Transactional
    @Query("delete from PollAnswers pa where pa.pollId = :pollId")
    void deleteAllByPollId(@Param("pollId") Polls pollId);
}
