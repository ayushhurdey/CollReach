package com.collreach.userprofile.model.repositories;

import com.collreach.userprofile.model.bo.CourseInfo;
import com.collreach.userprofile.model.bo.SkillsInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CourseInfoRepository extends CrudRepository<CourseInfo, Integer>{
    Optional<CourseInfo> findByBranchAndCourseNameAndSemester(String branch, String courseName, int semester);
}
