package com.collreach.userprofile.model.repositories;

import com.collreach.userprofile.model.bo.CourseInfo;
import com.collreach.userprofile.model.bo.UserPersonalInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserPersonalInfoRepository extends CrudRepository<UserPersonalInfo, Integer>{
    Boolean existsByEmail(String email);
    Boolean existsByAlternateEmail(String alternateEmail);
    Boolean existsByPhoneNo(String phoneNo);
    Boolean existsByLinkedinLink(String linkedinLink);

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
    @Query("update UserPersonalInfo u set u.alternateEmail = :email where u.alternateEmail = :oldEmail")
    void updateAlternateEmail(@Param(value = "oldEmail") String oldEmail, @Param(value = "email") String email);

    @Modifying
    @Transactional
    @Query("update UserPersonalInfo u set u.phoneNo = :phoneNo where u.phoneNo = :oldPhoneNo")
    void updatePhoneNo(@Param(value = "oldPhoneNo") String oldPhoneNo, @Param(value = "phoneNo") String phoneNo);

    @Modifying
    @Transactional
    @Query("update UserPersonalInfo u set u.courseInfo = :courseInfo where u.email = :email")
    void updateCourseId(@Param(value = "email") String email, @Param(value = "courseInfo") CourseInfo courseInfo);

    @Modifying
    @Transactional
    @Query("update UserPersonalInfo u set u.linkedinLink = :linkedinLink where u.linkedinLink = :oldLinkedinLink")
    void updateLinkedinLink(@Param(value = "oldLinkedinLink") String oldLinkedinLink, @Param(value = "linkedinLink") String linkedinLink);

    @Modifying
    @Transactional
    @Query("update UserPersonalInfo u set u.description = :description where u.email = :email")
    void updateDescription(@Param(value = "email") String email, @Param(value = "description") String description);
}
