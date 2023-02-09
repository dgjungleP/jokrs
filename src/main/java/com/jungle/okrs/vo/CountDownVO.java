package com.jungle.okrs.vo;

import lombok.Data;

import java.time.Instant;

@Data
public class CountDownVO {
    private String keyResultName;
    private String objectiveName;
    private Long leftTime;
    private Instant deadLine;
}
