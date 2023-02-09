package com.jungle.okrs.service;

import cn.hutool.core.util.ObjectUtil;
import com.jungle.okrs.db.JsonDB;
import com.jungle.okrs.entity.File;
import com.jungle.okrs.vo.FileTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@Service
public class FileService {

    @Autowired
    JsonDB jsonDB;

    public void tryEditDocument(File file) {
        if (ObjectUtil.isEmpty(file.getId())) {
            jsonDB.insertFile(file);
        }
        jsonDB.updateFile(file);
    }

    public FileTree getFileTree(File.FileType type) {
        FileTree tree = new FileTree();
        List<File> fileList = jsonDB.queryFileList(type);
        Queue<File> fileQueue = new LinkedList<>(fileList);
        while (!fileQueue.isEmpty()) {
            File currentFile = fileQueue.poll();
            if (ObjectUtil.isEmpty(currentFile.getParentId()) || tree.container(currentFile.getParentId())) {
                tree.add(currentFile);
                continue;
            }
            fileQueue.add(currentFile);
        }
        return tree;
    }
}
