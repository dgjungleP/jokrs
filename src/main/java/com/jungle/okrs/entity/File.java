package com.jungle.okrs.entity;

import lombok.Data;

import java.time.Instant;
import java.util.List;


@Data
public class File {

    private Long id;
    private Long parentId;
    private Boolean isFolder;
    private String content;
    private String title;
    private String creator;
    private String lastUpdater;
    private Instant createTime;
    private Instant updateTime;
    private List<String> tagList;
    private FileType type;
    // TODO 和skill联动

    public enum FileType {
        INSPIRATION, BLOG,
    }
}
