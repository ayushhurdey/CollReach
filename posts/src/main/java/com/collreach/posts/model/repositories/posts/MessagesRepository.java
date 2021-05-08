package com.collreach.posts.model.repositories.posts;

import com.collreach.posts.model.bo.posts.Messages;
import org.springframework.data.repository.CrudRepository;

public interface MessagesRepository extends CrudRepository<Messages, Integer> {
}
