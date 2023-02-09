package com.jungle.okrs.controller;

import com.jungle.okrs.dto.KeyResultDTO;
import com.jungle.okrs.entity.ActionLog;
import com.jungle.okrs.entity.KeyResult;
import com.jungle.okrs.entity.Objective;
import com.jungle.okrs.service.ActionService;
import com.jungle.okrs.service.OkrService;
import com.jungle.okrs.vo.KeyResultVO;
import com.jungle.okrs.vo.ObjectiveVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("jokrs/")
public class OkrController {

    @Resource
    OkrService okrService;
    @Resource
    ActionService actionService;

    @GetMapping("all/objectives")
    public ResponseEntity<List<ObjectiveVO>> queryAllObjective() {
        List<ObjectiveVO> result = okrService.queryAllObjective();
        return ResponseEntity.ok(result);
    }

    @PostMapping("objective")
    public ResponseEntity<Objective> updateObjective(@RequestBody Objective objective) {
        Objective result = okrService.updateObjective(objective);
        return ResponseEntity.ok(result);
    }

    @PutMapping("objective")
    public ResponseEntity<Objective> addObjective(@RequestBody Objective objective) {
        Objective result = okrService.addObjective(objective);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("objective")
    public ResponseEntity<String> deleteObjective(@RequestParam("objectiveId") Long id) {
        okrService.deleteObjective(id);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("all/key-result")
    public ResponseEntity<List<KeyResult>> queryAllKeyResult(@RequestParam("objectiveId") Long objectiveId) {
        List<KeyResult> result = okrService.queryAllKeyResult(objectiveId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("key-result")
    public ResponseEntity<KeyResult> updateKeyResult(@RequestBody KeyResultDTO keyResultDTO) {
        KeyResult result = okrService.updateKeyResult(keyResultDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("key-result")
    public ResponseEntity<String> deleteKeyResult(@RequestParam("keyResultId") Long id) {
        okrService.deleteKeyResult(id);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("key-result/{keyResultId}")
    public ResponseEntity<KeyResultVO> getKeyResultById(@PathVariable("keyResultId") Long id) {
        KeyResultVO result = okrService.getKeyResultById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("key-result")
    public ResponseEntity<KeyResult> addKeyResult(@RequestBody KeyResultDTO keyResultDTO) {
        KeyResult result = okrService.addKeyResult(keyResultDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("action/list")
    public ResponseEntity<List<ActionLog>> getActionLog() {
        List<ActionLog> result = actionService.getActionLogList();
        return ResponseEntity.ok(result);
    }
}
