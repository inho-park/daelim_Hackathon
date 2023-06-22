package com.daelim.daelim_hackathon.chapter.service;

import com.daelim.daelim_hackathon.chapter.domain.Chapter;
import com.daelim.daelim_hackathon.chapter.dto.ChapterDTO;
import com.daelim.daelim_hackathon.chapter.dto.ChapterModifyDTO;
import com.daelim.daelim_hackathon.chapter.dto.ChapterPageRequestDTO;
import com.daelim.daelim_hackathon.common.dto.PageResultDTO;
import com.daelim.daelim_hackathon.common.dto.StatusDTO;
import com.daelim.daelim_hackathon.novel.domain.Novel;

public interface ChapterService {
    StatusDTO saveChapter(ChapterDTO chapterDTO);
    ChapterDTO getChapter(Long chapterId);
    PageResultDTO<ChapterDTO, Object[]> getChapters(ChapterPageRequestDTO pageRequestDTO);
    StatusDTO deleteChapter(Long chapterId);
    StatusDTO updateChapter(Long chapterId, ChapterModifyDTO modifyDTO);
    ChapterDTO getNextChapter(Long prevId);

    default Chapter dtoToEntity(ChapterDTO dto) {

        return Chapter.builder()
                .chapterName(dto.getChapterName())
                .novel(Novel.builder().id(dto.getNovelId()).build())
                .prevChapter(dto.getPrevChapterId())
                .build();

    }

    default ChapterDTO entityToDTO(Chapter chapter, Novel novel) {

        return ChapterDTO.builder()
                .chapterId(chapter.getId())
                .chapterName(chapter.getChapterName())
                .prevChapterId(chapter.getPrevChapter())
                .novelId(novel.getId())
                .modDate(chapter.getModDate())
                .regDate(chapter.getRegDate())
                .build();
    }
}
