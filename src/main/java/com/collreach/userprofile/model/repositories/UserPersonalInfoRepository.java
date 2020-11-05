package com.collreach.userprofile.model.repositories;

import com.collreach.userprofile.model.bo.UserPersonalInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserPersonalInfoRepository extends CrudRepository<UserPersonalInfo, Integer>{
    Boolean existsByEmail(String email);
    Boolean existsByAlternateEmail(String alternateEmail);
    Boolean existsByPhoneNo(String phoneNo);
}
