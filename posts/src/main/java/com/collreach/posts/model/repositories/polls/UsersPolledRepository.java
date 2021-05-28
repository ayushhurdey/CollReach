package com.collreach.posts.model.repositories.polls;

import com.collreach.posts.model.bo.polls.Polls;
import com.collreach.posts.model.bo.polls.UserPollKey;
import com.collreach.posts.model.bo.polls.UsersPolled;
import com.collreach.posts.model.bo.posts.Messages;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UsersPolledRepository extends CrudRepository<UsersPolled, UserPollKey> {
    @Modifying
    @Transactional
    @Query("delete from UsersPolled u where u.pollId = :pollId")
    void deleteAllByPollId(@Param("pollId") Polls pollId);
}
