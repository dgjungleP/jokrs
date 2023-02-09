package com.jungle.okrs.entity;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.jungle.okrs.vo.CountDownVO;
import lombok.Data;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
public class KeyResult {
    private Long id;
    private String name;
    private String description;
    private Instant planTime;
    private Long measure;
    private Long progress = 0L;

    public boolean containTime(Instant time) {
        return DateUtil.date(time.toEpochMilli()).isBeforeOrEquals(Date.from(planTime));
    }

    public CountDownVO makeCountDown() {
        CountDownVO downVO = new CountDownVO();
        downVO.setKeyResultName(this.getName());
        downVO.setDeadLine(this.getPlanTime());
        downVO.setLeftTime(DateUtil.date().between(Date.from(this.getPlanTime()), DateUnit.DAY));
        return downVO;
    }
}
