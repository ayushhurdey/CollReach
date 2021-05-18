package com.collreach.userprofile.model.repositories;

import com.collreach.userprofile.model.bo.CourseInfo;
import com.collreach.userprofile.model.bo.SkillsInfo;
import com.collreach.userprofile.model.bo.UserPersonalInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserPersonalInfoRepository extends CrudRepository<UserPersonalInfo, Integer>{
    Boolean existsByEmail(String email);
    Boolean existsByAlternateEmail(String alternateEmail);
    Boolean existsByPhoneNo(String phoneNo);
    Boolean existsByLinkedinLink(String linkedinLink);
    Optional<UserPersonalInfo> findByProfileAccessKey(String profileAccessKey);
    Boolean existsByProfileAccessKey(String profileAccessKey);
    List<UserPersonalInfo> findAllByNameStartsWith(String name);

    @Modifying
    @Transactional
    @Query("delete from UserPersonalInfo u where u.userId = :userId")
    void deleteByUserId(@Param(value = "userId") int userId);


    @Modifying
    @Transactional
    @Query("update UserPersonalInfo u set u.email = :email where u.email = :oldEmail")
    void updateEmail(@Param(value = "oldEmail") String oldEmail, @Param(value = "email") String email);

    @Modifying
    @Transactional
    @Query("update UserPersonalInfo u set u.alternateEmail = :altEmail where u.email = :email")
    void updateAlternateEmail(@Param(value = "email") String email, @Param(value = "altEmail") String altEmail);

    @Modifying
    @Transactional
    @Query("update UserPersonalInfo u set u.phoneNo = :phoneNo where u.email = :email")
    void updatePhoneNo(@Param(value = "email") String email, @Param(value = "phoneNo") String phoneNo);

    @Modifying
    @Transactional
    @Query("update UserPersonalInfo u set u.courseInfo = :courseInfo where u.email = :email")
    void updateCourseId(@Param(value = "email") String email, @Param(value = "courseInfo") CourseInfo courseInfo);

    @Modifying
    @Transactional
    @Query("update UserPersonalInfo u set u.linkedinLink = :linkedinLink where u.email = :email")
    void updateLinkedinLink(@Param(value = "email") String email, @Param(value = "linkedinLink") String linkedinLink);

    @Modifying
    @Transactional
    @Query("update UserPersonalInfo u set u.description = :description where u.email = :email")
    void updateDescription(@Param(value = "email") String email, @Param(value = "description") String description);

    @Modifying
    @Transactional
    @Query("update UserPersonalInfo u set u.userProfilePhoto = :userProfilePhoto where u.email = :email")
    void updateUserProfilePhoto(@Param(value = "email") String email, @Param(value = "userProfilePhoto") String userProfilePhoto);

    @Modifying
    @Transactional
    @Query("update UserPersonalInfo u set u.miniUserProfilePhoto = :miniUserProfilePhoto where u.email = :email")
    void updateMiniUserProfilePhoto(@Param(value = "email") String email, @Param(value = "miniUserProfilePhoto") String miniUserProfilePhoto);
}
