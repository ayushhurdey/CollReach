package com.collreach.userprofile.model.repositories;

import com.collreach.userprofile.model.bo.UserLogin;
import org.springframework.data.repository.CrudRepository;

public interface UserLoginRepository extends CrudRepository<UserLogin, String> {
}
