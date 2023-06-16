package com.daelim.daelim_hackathon.chapter.service;

import com.daelim.daelim_hackathon.author.domain.User;
import com.daelim.daelim_hackathon.chapter.domain.Chapter;
import com.daelim.daelim_hackathon.chapter.dto.ChapterDTO;
import com.daelim.daelim_hackathon.chapter.dto.ChapterModifyDTO;
import com.daelim.daelim_hackathon.chapter.dto.ChapterPageRequestDTO;
import com.daelim.daelim_hackathon.common.dto.PageResultDTO;
import com.daelim.daelim_hackathon.common.dto.StatusDTO;
import com.daelim.daelim_hackathon.novel.domain.Novel;
import com.daelim.daelim_hackathon.novel.dto.NovelDTO;
import com.daelim.daelim_hackathon.novel.dto.NovelModifyDTO;
import com.daelim.daelim_hackathon.novel.dto.NovelPageRequestDTO;

public interface ChapterService {
    StatusDTO saveChapter(ChapterDTO chapterDTO);
    ChapterDTO getChapter(Long chapterId);
    PageResultDTO<ChapterDTO, Object[]> getChapters(ChapterPageRequestDTO pageRequestDTO);
    StatusDTO deleteChapter(Long chapterId, String username);
    StatusDTO updateChapter(Long chapterId, ChapterModifyDTO modifyDTO);

    default Chapter dtoToEntity(ChapterDTO dto, Novel novel) {

        Chapter chapter = Chapter.builder()
                .chapterName(dto.getChapterName())
                .totalPages(dto.getTotalPages())
                .novel(novel)
                .prevChapter(Chapter.builder().id(dto.getPrevChapterId()).build())
                .build();

        return chapter;

    }

    default ChapterDTO entityToDTO(Chapter chapter) {
        ChapterDTO chapterDTO = ChapterDTO.builder()
                .chapterId(chapter.getId())
                .chapterName(chapter.getChapterName())
                .totalPages(chapter.getTotalPages())
                .prevChapterId(chapter.getPrevChapter().getId())
                .novelId(chapter.getNovel().getId())
                .modDate(chapter.getModDate())
                .regDate(chapter.getRegDate())
                .build();

        return chapterDTO;
    }
}
