package com.jungle.okrs.dto;

import com.jungle.okrs.entity.KeyResult;
import com.jungle.okrs.entity.OKRRelation;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class KeyResultDTO extends KeyResult {
    private Long objectiveId;
    private Long weight;


    public OKRRelation makeRelation() {
        OKRRelation relation = new OKRRelation();
        relation.setKeyResultId(this.getId());
        relation.setObjectiveId(this.getObjectiveId());
        relation.setWeight(this.getWeight());
        return relation;
    }

    public KeyResult toKeyResult() {
        KeyResult result = new KeyResult();
        BeanUtils.copyProperties(this, result);
        return result;
    }
}
