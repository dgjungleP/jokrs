package com.jungle.okrs.vo;

import lombok.Data;

import java.time.Instant;

@Data
public class DailyTodoVO {
    private Long id;
    private String keyResultName;
    private String objectiveName;
    private Long expect = 0L;
    private Long current = 0L;
    private Long actual = 0L;
    private boolean finished;
}
