package com.jungle.okrs.vo;

import com.jungle.okrs.dto.KeyResultDTO;
import com.jungle.okrs.entity.KeyResult;
import com.jungle.okrs.entity.OKRRelation;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.Instant;

@Data
public class KeyResultVO {
    private Long id;
    private String name;
    private String description;
    private Instant planTime;
    private Long measure;
    private Long progress = 0L;
    private Long weight;
    private Long objectiveId;

    public static KeyResultVO build(KeyResult keyResult, OKRRelation relation) {
        KeyResultVO vo = new KeyResultVO();
        BeanUtils.copyProperties(keyResult, vo);
        BeanUtils.copyProperties(relation, vo);
        return vo;
    }
}
