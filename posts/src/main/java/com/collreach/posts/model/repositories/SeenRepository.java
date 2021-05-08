package com.collreach.posts.model.repositories;

import com.collreach.posts.model.bo.Seen;
import com.collreach.posts.model.bo.UserMessageKey;
import org.springframework.data.repository.CrudRepository;

public interface SeenRepository extends CrudRepository<Seen, UserMessageKey> {
}
