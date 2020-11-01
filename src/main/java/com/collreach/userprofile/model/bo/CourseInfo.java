package com.collreach.userprofile.model.bo;

import javax.persistence.*;

@Entity
public class CourseInfo {
    @Id
    @Column(name = "course_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int courseId;

    @Column(nullable = false)
    private String branch;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "year_of_study", nullable = false)
    private int yearOfStudy;

    @Column(nullable = false)
    private int semester;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
