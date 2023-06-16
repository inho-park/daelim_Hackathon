package com.daelim.daelim_hackathon.chapter.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChapterDTO {
    private Long chapterId;
    private String chapterName;
    private int totalPages;
    private Long prevChapterId;
    private Long novelId;
    private LocalDateTime regDate,modDate;
}
