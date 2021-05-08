package com.collreach.posts.model.repositories.posts;

import com.collreach.posts.model.bo.posts.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Integer> {
}
