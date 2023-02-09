package com.jungle.okrs.db;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jungle.okrs.dto.KeyResultDTO;
import com.jungle.okrs.entity.*;
import com.jungle.okrs.entity.File;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Data
@Slf4j
public class JsonDB {

    private final List<Objective> objectiveList = new ArrayList<>();
    private final List<KeyResult> keyResultList = new ArrayList<>();

    private final List<OKRRelation> okrRelationList = new ArrayList<>();
    private final List<ActionLog> actionLogList = new ArrayList<>();
    private final List<Skill> skillList = new ArrayList<>();
    private final List<SkillRelation> skillRelationList = new ArrayList<>();
    private final List<File> fileList = new ArrayList<>();
    private final List<DailyTodo> dailyTodoList = new ArrayList<>();
    //    public static final String db_path = "/db/jokrs.json";
    public static final String db_path = "C:\\Users\\jd53\\Documents\\GitHub\\Jungle_bakend_demo\\jokrs\\src\\main\\resources\\mem.json";

    public static JsonDB init() {
        JsonDB jsonDB = new JsonDB();
        jsonDB.doFetch();
        return jsonDB;
    }

    private void doFetch() {
        java.io.File file = new java.io.File(db_path);
        String dbJson = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            dbJson = reader.lines().collect(Collectors.joining("\n"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JSONObject object = JSON.parseObject(dbJson);
        if (!ObjectUtil.isNull(object)) {
            for (TableEnums tableEnums : TableEnums.values()) {
                tableEnums.tableListFunction.apply(this).clear();
                if (CollectionUtil.isNotEmpty(object.getJSONArray(tableEnums.tableName))) {
                    List javaList = object.getJSONArray(tableEnums.tableName).toJavaList(tableEnums.tableClass);
                    tableEnums.tableListFunction.apply(this)
                            .addAll(javaList);
                }
            }
        }
        System.out.println("JsonDb init success");
    }

    public void flush() {
        java.io.File file = new java.io.File(db_path);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            JSONObject db = new JSONObject();
            for (TableEnums tableEnums : TableEnums.values()) {
                db.put(tableEnums.tableName, tableEnums.tableListFunction.apply(this));
            }
            String str = db.toString(SerializerFeature.PrettyFormat, SerializerFeature.DisableCircularReferenceDetect);
            writer.write(str);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Objective> queryObjective() {
        return new ArrayList<>(objectiveList);
    }

    public Objective addObjective(Objective objective) {
        long currentId = calculateId(this.getObjectiveList().stream().mapToLong(Objective::getId));
        objective.setId(currentId);
        this.getObjectiveList().add(objective);
        flush();
        return objective;
    }

    private long calculateId(LongStream mapToLong) {
        return mapToLong.max().orElse(0L) + 1;
    }

    public Objective tryUpdateObjective(Objective objective) {
        Long id = objective.getId();
        Objective currentObjective = this.getObjectiveList().stream().filter(data -> data.getId().equals(id)).findAny()
                .orElseGet(() -> addObjective(objective));
        BeanUtils.copyProperties(objective, currentObjective, "id");
        flush();
        return currentObjective;
    }

    public List<KeyResult> queryAllKeyResult(Long objectiveId) {
        List<Long> keyResultList = this.getOkrRelationList().stream()
                .filter(data -> data.getObjectiveId().equals(objectiveId)).map(OKRRelation::getKeyResultId)
                .collect(Collectors.toList());

        return this.getKeyResultList().stream().filter(data -> keyResultList.contains(data.getId()))
                .collect(Collectors.toList());
    }

    public KeyResultDTO addKeyResult(KeyResultDTO keyResultDTO) {
        KeyResult keyResult = keyResultDTO.toKeyResult();
        long id = calculateId(this.getKeyResultList().stream().mapToLong(KeyResult::getId));
        keyResult.setId(id);
        this.getKeyResultList().add(keyResult);
        keyResultDTO.setId(keyResult.getId());
        flush();
        return keyResultDTO;

    }

    public KeyResultDTO tryUpdateKeyResult(KeyResultDTO keyResultDTO) {
        KeyResult keyResult = this.getKeyResultList().stream().filter(data -> data.getId().equals(keyResultDTO.getId()))
                .findAny().orElseGet(() -> {
                    KeyResultDTO result = addKeyResult(keyResultDTO);
                    return getKeyResultById(result.getId());
                });
        BeanUtils.copyProperties(keyResultDTO, keyResult);
        flush();
        return keyResultDTO;
    }

    private KeyResult getKeyResultById(Long id) {
        return this.getKeyResultList().stream().filter(data -> data.getId().equals(id)).findAny().orElse(null);
    }

    public void tryAddOKRRelation(OKRRelation okrRelation) {
        this.getOkrRelationList().stream()
                .filter(data -> ObjectUtil.equals(data.getObjectiveId(), okrRelation.getObjectiveId()))
                .filter(data -> ObjectUtil.equals(data.getKeyResultId(), okrRelation.getKeyResultId())).findAny()
                .orElseGet(() -> {
                    this.getOkrRelationList().add(okrRelation);
                    return okrRelation;
                }).setWeight(okrRelation.getWeight());
        flush();
    }


    public void deleteObjective(Long id) {
        this.getObjectiveList().removeIf(data -> data.getId().equals(id));
        flush();
    }

    public OKRRelation queryRelationWithKeyResultId(Long id) {
        return getOkrRelationList().stream().filter(data -> data.getKeyResultId().equals(id)).findFirst().orElse(null);
    }

    public KeyResult queryKeyResultById(Long id) {
        return getKeyResultList().stream().filter(data -> data.getId().equals(id)).findFirst().orElse(null);
    }

    public void deleteKeyResult(Long id) {
        getKeyResultList().removeIf(data -> data.getId().equals(id));
        deleteRelation(id);
        deleteDailyTodo(id);
        flush();
    }

    private void deleteDailyTodo(Long keyResultId) {
        getDailyTodoList().removeIf(data -> data.getRelationKeyResultId().equals(keyResultId));
    }

    private void deleteRelation(Long keyResultId) {
        getOkrRelationList().removeIf(data -> data.getKeyResultId().equals(keyResultId));
    }


    public List<KeyResult> getActiveKeyResultByTime(Instant time) {
        return getKeyResultList().stream().filter(data -> data.containTime(time)).collect(Collectors.toList());
    }

    public List<DailyTodo> getActiveDailyTodoByTime(Instant time) {
        return getDailyTodoList().stream().filter(data -> data.currentDate(time)).collect(Collectors.toList());
    }

    public DailyTodo saveOrUpdateDailyTodo(DailyTodo todo) {
        if (ObjectUtil.isNull(todo.getId())) {
            long currentId = calculateId(this.getDailyTodoList().stream().mapToLong(DailyTodo::getId));
            todo.setId(currentId);
            this.getDailyTodoList().add(todo);
        } else {
            getDailyTodoList().stream().filter(data -> data.getId().equals(todo.getId())).findAny()
                    .ifPresent(data -> BeanUtils.copyProperties(todo, data));
        }
        flush();
        return todo;
    }

    public DailyTodo queryDailyTodo(Long keyResultId, Instant time) {
        return getDailyTodoList().stream().filter(data -> data.currentDate(time))
                .filter(data -> ObjectUtil.equals(data.getRelationKeyResultId(), keyResultId)).findFirst().orElse(null);
    }

    public List<DailyTodo> queryDailyTodo(Long keyResultId) {
        return getDailyTodoList().stream().filter(data -> ObjectUtil.equals(data.getRelationKeyResultId(), keyResultId))
                .filter(DailyTodo::isFinished).collect(Collectors.toList());

    }

    public Objective queryObjectiveById(Long objectiveId) {
        return getObjectiveList().stream().filter(data -> data.getId().equals(objectiveId)).findFirst().orElse(null);

    }

    public DailyTodo updateDailyTodoById(DailyTodo update) {
        Optional<DailyTodo> todo = queryDailyTodoById(update.getId());
        DailyTodo dailyTodo = null;
        if (todo.isPresent()) {
            dailyTodo = todo.get();
            dailyTodo.setActual(update.getActual());
            dailyTodo.setFinished(true);
            dailyTodo.setFinishTime(Instant.now());
        }
        flush();
        return dailyTodo;
    }

    private Optional<DailyTodo> queryDailyTodoById(Long id) {
        return getDailyTodoList().stream().filter(data -> data.getId().equals(id))
                .findFirst();
    }

    public void addAction(ActionLog actionLog) {
        getActionLogList().add(actionLog);
        flush();
    }

    public List<Skill> queryAllSkill() {
        return getSkillList();
    }

    public List<SkillRelation> queryAllSkillRelation() {
        return getSkillRelationList();

    }

    public Skill tryUpdateSkill(Skill currentSkill) {
        if (ObjectUtil.isNotNull(currentSkill.getId())) {
            Skill finalCurrentSkill = currentSkill;
            Skill temp = getSkillList().stream().filter(data -> data.getId().equals(finalCurrentSkill.getId()))
                    .findAny()
                    .orElse(currentSkill);
            temp.setName(currentSkill.getName());
            temp.setType(currentSkill.getType());
            currentSkill = temp;
        } else {
            long currentId = calculateId(getSkillList().stream().mapToLong(Skill::getId));
            currentSkill.setId(currentId);
            getSkillList().add(currentSkill);
        }
        flush();
        return currentSkill;
    }

    public void addSkillRelation(SkillRelation relation) {
        getSkillRelationList().add(relation);
        flush();
    }
}
