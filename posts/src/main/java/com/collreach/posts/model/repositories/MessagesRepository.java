package com.collreach.posts.model.repositories;

import com.collreach.posts.model.bo.Messages;
import org.springframework.data.repository.CrudRepository;

public interface MessagesRepository extends CrudRepository<Messages, Integer> {
}
