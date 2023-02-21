package com.jungle.jokrs;


import cn.hutool.core.util.ObjectUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeightRegulator {
    public static final Long FULL = 100L;

    private final List<Relation> relationList;

    public WeightRegulator(List<Relation> relationList) {
        this.relationList = relationList;
    }

    public List<Relation> getCorrectedRelation() {
        Map<Boolean, List<Long>> relationMap = relationList.stream().map(Relation::getWeight)
                .collect(Collectors.groupingBy(WeightRegulator::checkWeightNotEmpty));
        long emptyCount = relationMap.get(false).size();
        if (emptyCount > 0) {
            long usedWeight = relationMap.get(true).stream().mapToLong(data -> data).sum();
            long avgWeight = (FULL - usedWeight) / emptyCount;
            relationList.stream().filter(data -> checkWeightNotEmpty(data.getWeight()))
                    .forEach(data -> data.setWeight(avgWeight));
        }
        return relationList;
    }

    private static boolean checkWeightNotEmpty(Long data) {
        return ObjectUtil.isNotNull(data) && data > 0L;
    }
}
