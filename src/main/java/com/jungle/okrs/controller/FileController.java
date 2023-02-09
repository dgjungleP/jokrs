package com.jungle.okrs.controller;

import com.jungle.okrs.entity.File;
import com.jungle.okrs.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService service;


    @PostMapping("/try/add")
    public ResponseEntity<String> tryEditDocument(@RequestBody File file) {


        return ResponseEntity.ok("");
    }

}
