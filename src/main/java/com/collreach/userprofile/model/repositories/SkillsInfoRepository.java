package com.collreach.userprofile.model.repositories;

import com.collreach.userprofile.model.bo.SkillsInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SkillsInfoRepository extends CrudRepository<SkillsInfo, Integer> {
    List<SkillsInfo> findAllBySkill(String skill);
    Optional<SkillsInfo> findBySkill(String skill);
}
