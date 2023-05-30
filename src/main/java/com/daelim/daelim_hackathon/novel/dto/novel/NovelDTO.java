package com.daelim.daelim_hackathon.novel.dto.novel;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NovelDTO {
    private Long novelId;
    private String title;
    private String hostUsername;
    private LocalDateTime regDate,modDate;
}
