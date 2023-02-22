package com.jungle.jokrs;

import com.jungle.okrs.util.strategy.Relation;
import com.jungle.okrs.util.strategy.WeightRegulator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestRegulator {

    @Test
    public void testRegulator() {
        List<Relation> list = new ArrayList<>();
        list.add(new MockRelation(10L));
        list.add(new MockRelation(20L));
        list.add(new MockRelation(0L));
        list.add(new MockRelation(0L));
        list.add(new MockRelation(0L));
        WeightRegulator regulator = new WeightRegulator(list);

        System.out.println(regulator.getCorrectedRelation());
    }
}
