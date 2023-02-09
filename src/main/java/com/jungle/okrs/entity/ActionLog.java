package com.jungle.okrs.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class ActionLog {
    private Instant time;
    private String actionDescription;
}
