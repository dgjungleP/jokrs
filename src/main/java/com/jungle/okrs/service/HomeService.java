package com.jungle.okrs.service;

import cn.hutool.core.date.DateUtil;
import com.jungle.okrs.db.JsonDB;
import com.jungle.okrs.entity.*;
import com.jungle.okrs.vo.CountDownVO;
import com.jungle.okrs.vo.DailyTodoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeService {
    @Autowired
    JsonDB jsonDB;
    @Resource
    ActionService actionService;

    public List<DailyTodoVO> getTodayTodo() {
        Instant time = Instant.now();
        List<KeyResult> currentKeyResult = jsonDB.getActiveKeyResultByTime(time);
        for (KeyResult result : currentKeyResult) {
            tryMakeTodo(result, time);
        }
        List<DailyTodo> activeDailyTodoByTime = jsonDB.getActiveDailyTodoByTime(time);
        return activeDailyTodoByTime.stream().map(this::makeVO).collect(Collectors.toList());
    }

    private DailyTodoVO makeVO(DailyTodo data) {
        Long keyResultId = data.getRelationKeyResultId();
        DailyTodoVO vo = new DailyTodoVO();
        BeanUtils.copyProperties(data, vo);
        KeyResult keyResult = jsonDB.queryKeyResultById(keyResultId);
        OKRRelation relation = jsonDB.queryRelationWithKeyResultId(keyResultId);
        Objective objective = jsonDB.queryObjectiveById(relation.getObjectiveId());
        vo.setKeyResultName(keyResult.getName());
        vo.setObjectiveName(objective.getName());
        return vo;
    }

    private void tryMakeTodo(KeyResult result, Instant time) {
        DailyTodo dailyTodo = jsonDB.queryDailyTodo(result.getId(), time);
        if (dailyTodo == null) {
            dailyTodo = new DailyTodo();
            dailyTodo.setRelationKeyResultId(result.getId());
            dailyTodo.setCreateTime(Instant.now());
        }
        List<DailyTodo> finishDailyTodo = jsonDB.queryDailyTodo(result.getId());
        long actualFinish = finishDailyTodo.stream().mapToLong(DailyTodo::getActual).sum();
        result.setProgress(actualFinish);
        dailyTodo.setCurrent(result.getProgress());
        dailyTodo.setExpect((result.getMeasure() - result.getProgress()) / (DateUtil.betweenDay(Date.from(result.getPlanTime()), Date.from(time), true) + 1));
        jsonDB.saveOrUpdateDailyTodo(dailyTodo);
    }


    public void applyTodo(DailyTodo dailyTodo) {
        DailyTodo todo = jsonDB.updateDailyTodoById(dailyTodo);
        DailyTodoVO dailyTodoVO = makeVO(todo);
        ActionLog actionLog = new ActionLog();
        actionLog.setTime(Instant.now());
        actionLog.setActionDescription("Apply O{" + dailyTodoVO.getObjectiveName() + "}`s KR{" + dailyTodoVO.getKeyResultName() + "} with progress{" + dailyTodoVO.getActual() + "}");
        actionService.addAction(actionLog);
    }

    public List<CountDownVO> getCountDown() {
        List<KeyResult> keyResultList = jsonDB.getActiveKeyResultByTime(Instant.now());
        return keyResultList.stream().map(KeyResult::makeCountDown).collect(Collectors.toList());
    }
}
