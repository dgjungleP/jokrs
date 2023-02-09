package com.jungle.okrs.entity;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;

@Data
public class SkillRelation {
    private Long sourceId;
    private Long targetId;

    public static SkillRelation tryCreateRelation(Long targetId, Long sourceId) {
        SkillRelation skillRelation = null;
        if (ObjectUtil.isAllNotEmpty(targetId, sourceId)) {
            skillRelation = new SkillRelation();
            skillRelation.sourceId = sourceId;
            skillRelation.targetId = targetId;
        }
        return skillRelation;
    }
}
