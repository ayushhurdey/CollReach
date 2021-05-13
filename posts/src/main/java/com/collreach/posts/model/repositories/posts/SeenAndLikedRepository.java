package com.collreach.posts.model.repositories.posts;

import com.collreach.posts.model.bo.posts.SeenAndLiked;
import com.collreach.posts.model.bo.posts.UserMessageKey;
import org.springframework.data.repository.CrudRepository;

public interface SeenAndLikedRepository extends CrudRepository<SeenAndLiked, UserMessageKey> {
}
