package com.jungle.okrs.dto;

import com.jungle.okrs.entity.Skill;
import com.jungle.okrs.entity.SkillType;
import lombok.Data;

@Data
public class SkillUpdateDTO {
    private Long id;
    private String name;
    private Long relation;
    private String type;

    public Skill makeSkill() {
        Skill skill = new Skill();
        skill.setName(this.name);
        skill.setId(this.id);
        skill.setType(new SkillType(this.type));
        return skill;

    }
}
