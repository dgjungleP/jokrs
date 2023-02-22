package com.jungle.okrs.util.strategy;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeightRegulator<T extends Relation> {
    public static final Long FULL = 100L;

    private final List<T> relationList;

    public WeightRegulator(List<T> relationList) {
        this.relationList = (List<T>) JSON.parseArray(JSON.toJSONString(relationList))
                .toJavaList(CollectionUtil.getFirst(relationList).getClass());
    }

    public List<T> getCorrectedRelation() {
        Map<Boolean, List<Long>> relationMap = relationList.stream().map(T::getWeight)
                .collect(Collectors.groupingBy(WeightRegulator::checkWeightNotEmpty));
        long emptyCount = relationMap.getOrDefault(false, new ArrayList<>()).size();
        if (emptyCount > 0) {
            long usedWeight = relationMap.getOrDefault(true, new ArrayList<>()).stream().mapToLong(data -> data).sum();
            long avgWeight = Math.max((FULL - usedWeight) / emptyCount, 0);
            long tail = FULL - usedWeight - avgWeight * emptyCount;
            relationList.stream().filter(data -> !checkWeightNotEmpty(data.getWeight()))
                    .peek(data -> data.setWeight(avgWeight)).findAny()
                    .ifPresent(data -> data.setWeight(data.getWeight() + tail));
            relationList.stream().filter(data -> !checkWeightNotEmpty(data.getWeight()))
                    .forEach(data -> data.setWeight(avgWeight));

        }
        return relationList;
    }

    private static boolean checkWeightNotEmpty(Long data) {
        return ObjectUtil.isNotNull(data) && data > 0L;
    }
}
