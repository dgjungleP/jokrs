package com.jungle.okrs.db;

import com.jungle.okrs.entity.*;

import java.util.List;
import java.util.function.Function;

public enum TableEnums {
    OBJECTIVE("objective", Objective.class, JsonDB::getObjectiveList),
    KEY_RESULT("key_result", KeyResult.class, JsonDB::getKeyResultList),
    OKR_RELATION("okr_relation", OKRRelation.class, JsonDB::getOkrRelationList),
    ACTION_LOG("action_log", ActionLog.class, JsonDB::getActionLogList),
    SKILL("skill", Skill.class, JsonDB::getSkillList),
    SKILL_RELATION("skill_relation", SkillRelation.class, JsonDB::getSkillRelationList),
    FILE("file", File.class, JsonDB::getFileList),
    DAILY_TODO("daily_todo", DailyTodo.class, JsonDB::getDailyTodoList);

    public final String tableName;
    public final Class<?> tableClass;
    public final Function<JsonDB, List<?>> tableListFunction;


    TableEnums(String tableName, Class<?> tableClass, Function<JsonDB, List<?>> tableListFunction) {
        this.tableName = tableName;
        this.tableClass = tableClass;
        this.tableListFunction = tableListFunction;
    }
}
