package com.daelim.daelim_hackathon.chapter.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChapterDTO {
    private Long chapterId;
    private String chapterName;
    private int totalPages;
    private LocalDateTime regDate,modDate;
}
