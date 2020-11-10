package com.collreach.userprofile.model.bo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


public class UserSkillsKey implements Serializable {
    private int userId;
    private int skillId;

    public UserSkillsKey() {
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
