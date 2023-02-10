package com.jungle.okrs.controller;

import com.jungle.okrs.entity.File;
import com.jungle.okrs.service.FileService;
import com.jungle.okrs.vo.FileTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService service;


    @PostMapping("/try/add")
    public ResponseEntity<String> tryEditDocument(@RequestBody File file) {
        service.tryEditDocument(file);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/tree")
    public ResponseEntity<List<FileTree.Node<File>>> getFileTree(@RequestParam(value = "type", required = false) File.FileType type) {
        FileTree tree = service.getFileTree(type);
        return ResponseEntity.ok(tree.getRoot().getFiles());
    }

    @GetMapping("/folder")
    public ResponseEntity<List<File>> getFolderList() {
        FileTree tree = service.getFileTree(null);
        return ResponseEntity.ok(tree.getAllFolder());
    }

    @GetMapping("")
    public ResponseEntity<File> getFileTree(@RequestParam("id") Long id) {
        File tree = service.getFile(id);
        return ResponseEntity.ok(tree);
    }
}
