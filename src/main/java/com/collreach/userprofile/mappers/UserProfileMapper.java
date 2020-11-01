package com.collreach.userprofile.mappers;

import com.collreach.userprofile.model.bo.CourseInfo;
import com.collreach.userprofile.model.bo.UserLogin;
import com.collreach.userprofile.model.bo.UserPersonalInfo;
import com.collreach.userprofile.model.response.CourseInfoResponse;
import com.collreach.userprofile.model.response.UserLoginResponse;
import com.collreach.userprofile.model.response.UserPersonalInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface UserProfileMapper {
    @Mappings({
            @Mapping(target="userPersonalInfoResponse", source = "userPersonalInfo")})
    public UserLoginResponse userLoginToUserLoginResponse(UserLogin userLogin);
    @Mappings({
            @Mapping(target="courseInfoResponse", source = "courseInfo")})
    public UserPersonalInfoResponse userPersonalInfoToUserPersonalInfoResponse(UserPersonalInfo userPersonalInfo);
    public CourseInfoResponse courseInfoToCourseInfoResponse(CourseInfo courseInfo);
}
