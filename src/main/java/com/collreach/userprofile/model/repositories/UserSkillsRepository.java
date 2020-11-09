package com.collreach.userprofile.model.repositories;

import com.collreach.userprofile.model.bo.SkillsInfo;
import com.collreach.userprofile.model.bo.UserPersonalInfo;
import com.collreach.userprofile.model.bo.UserSkills;
import com.collreach.userprofile.model.bo.UserSkillsKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserSkillsRepository extends CrudRepository<UserSkills, UserSkillsKey> {

    //@Query("select u from UserSkills u where u.userId = :userId")
    //public List<UserSkills> findAllByUserId(@Param(value = "userId") int userId);
    List<UserSkills> findAllByUserId(UserPersonalInfo userId);

    //List<UserSkills> findByUserId(@Param("userId") int UserId);
}
