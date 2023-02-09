package com.jungle.okrs.entity;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
public class DailyTodo {
    private Long id;
    private Long relationKeyResultId;
    private Long expect;
    private Long current;
    private Long actual;
    private Instant createTime;
    private Instant finishTime;
    private boolean finished;

    public boolean currentDate(Instant time) {
        return DateUtil.isSameDay(Date.from(createTime), Date.from(time));
    }
}
