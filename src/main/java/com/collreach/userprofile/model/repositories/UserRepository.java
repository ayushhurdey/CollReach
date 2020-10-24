package com.collreach.userprofile.model.repositories;

import com.collreach.userprofile.model.bo.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
