package com.jungle.okrs.vo;

import com.jungle.okrs.entity.Objective;
import lombok.Data;

import java.time.Instant;

@Data
public class ObjectiveVO {
    private Long id;
    private String name;
    private Instant planTime;
    private String description;
    private Double progress;

}
