package com.collreach.posts.model.repositories.posts;

import com.collreach.posts.model.bo.posts.Seen;
import com.collreach.posts.model.bo.posts.UserMessageKey;
import org.springframework.data.repository.CrudRepository;

public interface SeenRepository extends CrudRepository<Seen, UserMessageKey> {
}
