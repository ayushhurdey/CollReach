package com.collreach.posts.model.repositories;

import com.collreach.posts.model.bo.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Integer> {
}
