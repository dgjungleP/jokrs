package com.jungle.okrs.entity;

import lombok.Data;

@Data
public class SkillType {

    private String name;

    public SkillType(String name) {
        this.name = name;
    }

    public SkillType() {
    }
}
