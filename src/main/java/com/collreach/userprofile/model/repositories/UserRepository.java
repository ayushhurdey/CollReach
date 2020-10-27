package com.collreach.userprofile.model.repositories;

import com.collreach.userprofile.model.bo.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByuserName(String userName);
}
