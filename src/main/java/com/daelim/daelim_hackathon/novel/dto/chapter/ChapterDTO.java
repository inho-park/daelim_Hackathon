package com.daelim.daelim_hackathon.novel.dto.chapter;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChapterDTO {
    private Long chapterId;
    private String chapterName;
    private int totalPages;
    private LocalDateTime regDate,modDate;
}
