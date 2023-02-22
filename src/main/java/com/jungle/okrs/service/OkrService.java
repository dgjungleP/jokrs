package com.jungle.okrs.service;

import cn.hutool.core.util.ObjectUtil;
import com.jungle.okrs.db.JsonDB;
import com.jungle.okrs.dto.KeyResultDTO;
import com.jungle.okrs.entity.KeyResult;
import com.jungle.okrs.entity.OKRRelation;
import com.jungle.okrs.entity.Objective;
import com.jungle.okrs.util.strategy.WeightRegulator;
import com.jungle.okrs.vo.KeyResultVO;
import com.jungle.okrs.vo.ObjectiveVO;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OkrService {

    @Resource(name = "jsonDB")
    JsonDB jsonDB;


    public List<ObjectiveVO> queryAllObjective() {
        List<ObjectiveVO> result = new ArrayList<>();
        List<Objective> objectives = jsonDB.queryObjective();
        List<OKRRelation> okrRelationList = jsonDB.getOkrRelationList();
        List<KeyResult> keyResultList = jsonDB.getKeyResultList();
        for (Objective objective : objectives) {
            WeightRegulator<OKRRelation> regulator = new WeightRegulator<>(okrRelationList.stream()
                    .filter(data -> data.getObjectiveId().equals(objective.getId())).collect(Collectors.toList()));
            Double progress = regulator.getCorrectedRelation().stream()
                    .filter(data -> data.getObjectiveId().equals(objective.getId()))
                    .map(data -> keyResultList.stream().filter(kr -> kr.getId().equals(data.getKeyResultId()))
                            .map(kr -> calculateRate(kr) * data.getWeight())
                            .collect(Collectors.toList()))
                    .flatMap(Collection::stream)
                    .mapToDouble(data -> data)
                    .sum();
            result.add(objective.withProgress(progress));
        }
        return result;
    }

    private static void calculateWeight(List<OKRRelation> okrRelationList, Long objectiveId) {
        long emptyCount = okrRelationList.stream()
                .filter(data -> data.getObjectiveId().equals(objectiveId))
                .filter(data -> ObjectUtil.isNull(data.getWeight())).count();
        if (emptyCount > 0) {
            long sum = okrRelationList.stream()
                    .filter(data -> data.getObjectiveId().equals(objectiveId))
                    .filter(data -> ObjectUtil.isNotNull(data.getWeight()))
                    .mapToLong(OKRRelation::getWeight).sum();
            long commonWeight = (100 - sum) / emptyCount;
            okrRelationList.stream()
                    .filter(data -> data.getObjectiveId().equals(objectiveId))
                    .filter(data -> ObjectUtil.isNull(data.getWeight()))
                    .forEach(data -> data.setWeight(commonWeight));
        }
    }

    private static Double calculateRate(KeyResult kr) {
        Long progress = kr.getProgress();
        Long measure = kr.getMeasure();
        if (progress > measure) {
            return 1D;
        }
        return (double) progress / measure;
    }

    public Objective updateObjective(Objective objective) {
        return jsonDB.tryUpdateObjective(objective);
    }

    public Objective addObjective(Objective objective) {
        return jsonDB.addObjective(objective);
    }

    public List<KeyResult> queryAllKeyResult(Long objectiveId) {
        return jsonDB.queryAllKeyResult(objectiveId);
    }

    public KeyResult updateKeyResult(KeyResultDTO keyResultDTO) {
        KeyResultDTO result = jsonDB.tryUpdateKeyResult(keyResultDTO);
        OKRRelation okrRelation = result.makeRelation();
        jsonDB.tryAddOKRRelation(okrRelation);
        return result.toKeyResult();
    }

    public KeyResult addKeyResult(KeyResultDTO keyResultDTO) {
        KeyResultDTO result = jsonDB.addKeyResult(keyResultDTO);
        OKRRelation okrRelation = result.makeRelation();
        jsonDB.tryAddOKRRelation(okrRelation);
        return result.toKeyResult();
    }

    public void deleteObjective(Long id) {
        jsonDB.deleteObjective(id);
        List<KeyResult> keyResults = jsonDB.queryAllKeyResult(id);
        for (KeyResult result : keyResults) {
            jsonDB.deleteKeyResult(result.getId());
        }
    }

    public KeyResultVO getKeyResultById(Long id) {
        List<OKRRelation> okrRelationList = jsonDB.getOkrRelationList();
        OKRRelation relation = jsonDB.queryRelationWithKeyResultId(id);
        KeyResult keyResult = jsonDB.queryKeyResultById(id);
        WeightRegulator<OKRRelation> regulator = new WeightRegulator<>(okrRelationList.stream()
                .filter(data -> data.getObjectiveId().equals(relation.getObjectiveId())).collect(Collectors.toList()));
        KeyResultVO resultVo = KeyResultVO.build(keyResult, relation);
        regulator.getCorrectedRelation().stream().filter(data -> data.getKeyResultId().equals(id)).findAny()
                .ifPresent(data -> resultVo.setWeight(data.getWeight()));
        return resultVo;
    }

    public void deleteKeyResult(Long id) {
        jsonDB.deleteKeyResult(id);
    }
}
