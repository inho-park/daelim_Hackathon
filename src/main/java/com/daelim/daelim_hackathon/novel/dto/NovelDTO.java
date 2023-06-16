package com.daelim.daelim_hackathon.novel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NovelDTO {
    private Long novelId;
    private String title;
    private String username;
    private LocalDateTime regDate,modDate;
}
