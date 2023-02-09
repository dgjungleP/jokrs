package com.jungle.okrs.controller;

import com.jungle.okrs.entity.DailyTodo;
import com.jungle.okrs.service.HomeService;
import com.jungle.okrs.vo.CountDownVO;
import com.jungle.okrs.vo.DailyTodoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("home/")
public class HomeController {

    @Autowired
    HomeService homeService;

    @GetMapping("todo/list")
    public ResponseEntity<List<DailyTodoVO>> getTodayTodo() {
        List<DailyTodoVO> result = homeService.getTodayTodo();
        return ResponseEntity.ok(result);
    }

    @PostMapping("todo")
    public ResponseEntity<String> applyTodo(@RequestBody DailyTodo dailyTodo) {
        homeService.applyTodo(dailyTodo);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("count-down/list")
    public ResponseEntity<List<CountDownVO>> getCountDown() {
        List<CountDownVO> result = homeService.getCountDown();
        return ResponseEntity.ok(result);
    }
}
