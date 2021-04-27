package com.collreach.userprofile.model.bo;

import javax.persistence.*;


@Entity
@Table(name= "user_skills")
@IdClass(UserSkillsKey.class)
public class UserSkills{

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserPersonalInfo userId;

    @Id
    @ManyToOne
    @JoinColumn(name = "skill_id")
    private SkillsInfo skillId;


    @Column(name = "skill_upvote_count")
    private int skillUpvoteCount;

    public UserSkills(){
    }

    public UserPersonalInfo getUserId() {
        return userId;
    }

    public void setUserId(UserPersonalInfo userId) {
        this.userId = userId;
    }

    public SkillsInfo getSkillId() {
        return skillId;
    }

    public void setSkillId(SkillsInfo skillId) {
        this.skillId = skillId;
    }

    public int getSkillUpvoteCount() {
        return skillUpvoteCount;
    }

    public void setSkillUpvoteCount(int skillUpvoteCount) {
        this.skillUpvoteCount = skillUpvoteCount;
    }


    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSkills userSkills = (UserSkills) o;
        return userSkillsKey.equals(userSkills.userSkillsKey) &&
                skillUpvoteCount == (userSkills.skillUpvoteCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userSkillsKey, skillUpvoteCount);
    }
     */
}
