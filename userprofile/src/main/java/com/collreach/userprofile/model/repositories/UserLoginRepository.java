package com.collreach.userprofile.model.repositories;

import com.collreach.userprofile.model.bo.UserLogin;
import com.collreach.userprofile.model.bo.UserPersonalInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserLoginRepository extends CrudRepository<UserLogin, String> {
    Optional<UserLogin> findByUserName(String userName);

    @Modifying
    @Transactional
    @Query("update UserLogin u set u.password = :newPass where u.userName = :username")
    void updatePassword(@Param(value = "username") String username , @Param(value = "newPass") String newPassword);

    @Modifying
    @Transactional
    @Query("update UserLogin u set u.userName = :newUserName where u.userName = :username")
    void updateUserName(@Param(value = "username") String username , @Param(value = "newUserName") String newUserName);

    @Modifying
    @Transactional
    @Query("update UserLogin u set u.userPersonalInfo = :userId where u.userName = :username")
    void updateUserPersonalInfo(@Param(value = "username") String username , @Param(value = "userId")UserPersonalInfo userId);
}
