package com.jungle.okrs.service;

import com.jungle.okrs.db.JsonDB;
import com.jungle.okrs.entity.ActionLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionService {


    @Autowired
    JsonDB jsonDB;

    public void addAction(ActionLog actionLog) {
        jsonDB.addAction(actionLog);
    }

    public List<ActionLog> getActionLogList() {
        return jsonDB.getActionLogList();
    }
}
