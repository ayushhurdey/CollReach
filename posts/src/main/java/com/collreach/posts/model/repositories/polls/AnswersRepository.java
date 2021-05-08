package com.collreach.posts.model.repositories.polls;

import com.collreach.posts.model.bo.polls.Answers;
import org.springframework.data.repository.CrudRepository;

public interface AnswersRepository extends CrudRepository<Answers, Integer> {
}
