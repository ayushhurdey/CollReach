package com.collreach.userprofile.model.bo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SkillsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private int skillId;

    @Column(nullable = false, unique = true)
    private String skill;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "userSkills")
    private Set<UserPersonalInfo> userInfo;

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Set<UserPersonalInfo> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Set<UserPersonalInfo> userInfo) {
        this.userInfo = userInfo;
    }
}
