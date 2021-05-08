package com.collreach.posts.model.repositories.polls;

import com.collreach.posts.model.bo.polls.UserPollKey;
import com.collreach.posts.model.bo.polls.UsersPolled;
import org.springframework.data.repository.CrudRepository;

public interface UsersPolledRepository extends CrudRepository<UsersPolled, UserPollKey> {
}
