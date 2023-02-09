package com.jungle.okrs.controller;

import com.jungle.okrs.dto.SkillUpdateDTO;
import com.jungle.okrs.service.SkillService;
import com.jungle.okrs.vo.SkillVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("skill/")
public class SkillController {
    @Autowired
    SkillService service;


    @GetMapping("list")
    public ResponseEntity<SkillVo> getSkillList() {
        SkillVo result = service.getSkillList();

        return ResponseEntity.ok(result);
    }

    @PostMapping("add")
    public ResponseEntity<SkillVo> addSkill(@RequestBody SkillUpdateDTO updateDTO) {
        SkillVo result = service.addSkill(updateDTO);
        return ResponseEntity.ok(result);
    }

}
