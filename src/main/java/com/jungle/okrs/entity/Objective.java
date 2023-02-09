package com.jungle.okrs.entity;

import com.jungle.okrs.vo.ObjectiveVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.Instant;


@Data
public class Objective {
    private Long id;
    private String name;
    private Instant planTime;
    private String description;


    public ObjectiveVO withProgress(Double progress) {
        ObjectiveVO vo = new ObjectiveVO();
        BeanUtils.copyProperties(this, vo);
        vo.setProgress(progress);
        return vo;
    }
}
