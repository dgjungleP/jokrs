package com.jungle.okrs.service;

import cn.hutool.core.util.ObjectUtil;
import com.jungle.okrs.db.JsonDB;
import com.jungle.okrs.dto.SkillUpdateDTO;
import com.jungle.okrs.entity.Skill;
import com.jungle.okrs.entity.SkillRelation;
import com.jungle.okrs.vo.SkillVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService {

    @Autowired
    JsonDB jsonDB;

    public SkillVo getSkillList() {
        SkillVo vo = new SkillVo();
        List<Skill> skillList = jsonDB.queryAllSkill();
        List<SkillRelation> skillRelations = jsonDB.queryAllSkillRelation();
        vo.setSkillList(skillList);
        vo.setRelationList(skillRelations);
        vo.setSkillTypeList(skillList.stream().map(Skill::getType).distinct().collect(Collectors.toList()));
        return vo;
    }

    public SkillVo addSkill(SkillUpdateDTO updateDTO) {
        SkillVo vo = new SkillVo();
        Skill currentSkill = updateDTO.makeSkill();
        currentSkill = jsonDB.tryUpdateSkill(currentSkill);
        vo.getSkillList().add(currentSkill);
        SkillRelation relation = SkillRelation.tryCreateRelation(currentSkill.getId(), updateDTO.getRelation());
        if (ObjectUtil.isNotNull(relation)) {
            jsonDB.addSkillRelation(relation);
            vo.getRelationList().add(relation);
        }
        vo.getSkillTypeList().add(currentSkill.getType());
        return vo;
    }
}
