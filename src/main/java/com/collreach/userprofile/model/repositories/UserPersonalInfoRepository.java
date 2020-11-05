package com.collreach.userprofile.model.repositories;

import com.collreach.userprofile.model.bo.UserPersonalInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserPersonalInfoRepository extends CrudRepository<UserPersonalInfo, Integer>{
    Boolean existsByEmail(String email);
    Boolean existsByAlternateEmail(String alternateEmail);
    Boolean existsByPhoneNo(String phoneNo);

    @Modifying
    @Query("update UserPersonalInfo u set u.email = :email where u.email = :oldEmail")
    void updateEmail(@Param(value = "oldEmail") String oldEmail, @Param(value = "email") String email);
}
