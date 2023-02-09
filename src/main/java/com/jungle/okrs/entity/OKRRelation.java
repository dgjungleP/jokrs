package com.jungle.okrs.entity;

import lombok.Data;

@Data
public class OKRRelation {
    private Long weight;
    private Long objectiveId;
    private Long keyResultId;

    public static OKRRelation init() {
        OKRRelation relation = new OKRRelation();
        relation.setWeight(100L);
        return relation;
    }
}
