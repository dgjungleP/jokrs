package com.jungle.okrs.vo;

import com.alibaba.fastjson.JSON;
import com.jungle.okrs.entity.Skill;
import com.jungle.okrs.entity.SkillRelation;
import com.jungle.okrs.entity.SkillType;
import lombok.Data;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.util.ArrayList;
import java.util.List;

@Data
public class SkillVo {
    private List<Skill> skillList = new ArrayList<>();
    private List<SkillRelation> relationList = new ArrayList<>();
    private List<SkillType> skillTypeList = new ArrayList<>();

    public void tryAddRelation(Long targetId, Long sourceId) {
    }


}
