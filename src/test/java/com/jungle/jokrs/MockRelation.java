package com.jungle.jokrs;

import com.jungle.okrs.util.strategy.Relation;
import lombok.Data;

@Data
public class MockRelation implements Relation {

    private Long weight;

    public MockRelation(Long weight) {
        this.weight = weight;
    }
}
