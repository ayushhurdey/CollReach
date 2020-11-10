package com.collreach.userprofile.model.repositories;

import com.collreach.userprofile.model.bo.UserPersonalInfo;
import com.collreach.userprofile.model.bo.UserSkills;
import com.collreach.userprofile.model.bo.UserSkillsKey;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserSkillsRepository extends CrudRepository<UserSkills, UserSkillsKey> {

    List<UserSkills> findAllByUserId(UserPersonalInfo userId);

    @Modifying
    @Transactional
    @Query("delete from UserSkills u where u.userId = :userId")
    void deleteByUserId(@Param("userId") UserPersonalInfo userId);

}
