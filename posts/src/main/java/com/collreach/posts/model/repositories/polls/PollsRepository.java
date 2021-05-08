package com.collreach.posts.model.repositories.polls;

import com.collreach.posts.model.bo.polls.Polls;
import org.springframework.data.repository.CrudRepository;

public interface PollsRepository extends CrudRepository<Polls, Integer> {
}
