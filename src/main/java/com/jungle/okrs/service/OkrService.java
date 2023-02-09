package com.jungle.okrs.service;

import com.jungle.okrs.db.JsonDB;
import com.jungle.okrs.dto.KeyResultDTO;
import com.jungle.okrs.entity.KeyResult;
import com.jungle.okrs.entity.OKRRelation;
import com.jungle.okrs.entity.Objective;
import com.jungle.okrs.vo.KeyResultVO;
import com.jungle.okrs.vo.ObjectiveVO;
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
            Double progress = okrRelationList.stream()
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
        OKRRelation relation = jsonDB.queryRelationWithKeyResultId(id);
        KeyResult keyResult = jsonDB.queryKeyResultById(id);
        return KeyResultVO.build(keyResult, relation);
    }

    public void deleteKeyResult(Long id) {
        jsonDB.deleteKeyResult(id);
    }
}
