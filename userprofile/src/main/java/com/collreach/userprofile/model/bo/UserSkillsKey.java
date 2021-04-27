package com.collreach.userprofile.model.bo;

import java.io.Serializable;
import java.util.Objects;


public class UserSkillsKey implements Serializable {
    private int userId;
    private int skillId;

    public UserSkillsKey() {
    }

    public UserSkillsKey(int userId, int skillId) {
        this.userId = userId;
        this.skillId = skillId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSkillsKey userSkillsKey = (UserSkillsKey) o;
        return userId == (userSkillsKey.userId) &&
                skillId == (userSkillsKey.skillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, skillId);
    }

}
