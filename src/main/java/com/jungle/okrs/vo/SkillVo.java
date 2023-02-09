package com.jungle.okrs.vo;

import com.jungle.okrs.entity.Skill;
import com.jungle.okrs.entity.SkillRelation;
import com.jungle.okrs.entity.SkillType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SkillVo {
    private List<Skill> skillList = new ArrayList<>();
    private List<SkillRelation> relationList = new ArrayList<>();
    private List<SkillType> skillTypeList = new ArrayList<>();

    public void tryAddRelation(Long targetId, Long sourceId) {
    }
}
