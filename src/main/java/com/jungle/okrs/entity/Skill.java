package com.jungle.okrs.entity;

import lombok.Data;

@Data
public class Skill {

    private Long id;
    private String name;
    private SkillType type;
}
