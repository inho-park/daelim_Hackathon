package com.daelim.daelim_hackathon.chapter.service;

import com.daelim.daelim_hackathon.chapter.domain.Chapter;
import com.daelim.daelim_hackathon.chapter.dto.ChapterDTO;
import com.daelim.daelim_hackathon.chapter.dto.ChapterModifyDTO;
import com.daelim.daelim_hackathon.chapter.dto.ChapterPageRequestDTO;
import com.daelim.daelim_hackathon.chapter.repo.ChapterRepository;
import com.daelim.daelim_hackathon.common.config.dto.PageResultDTO;
import com.daelim.daelim_hackathon.common.config.dto.StatusDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService{

    private final ChapterRepository chapterRepository;

    @Override
    public StatusDTO saveChapter(ChapterDTO chapterDTO) {
        try {
            entityToDTO(chapterRepository.save(dtoToEntity(chapterDTO)));
            return StatusDTO.builder().status("success").build();
        } catch(Exception e) {
            throw e;
        }
    }

    @Override
    public ChapterDTO getChapter(Long chapterId) {
        Optional<Chapter> chapter = chapterRepository.findById(chapterId);
        if (chapter.isPresent()) {
            return entityToDTO(chapter.get());
        } else {
            throw new RuntimeException("chapter isn't exist");
        }
    }

    @Override
    public ChapterDTO getNextChapter(Long prevId) {
        Optional<Chapter> chapter = chapterRepository.findByPrevChapter_Id(prevId);
        if (chapter.isPresent()) {
            return entityToDTO(chapter.get());
        } else {
            throw new RuntimeException("next chapter isn't exist");
        }
    }

    @Override
    public PageResultDTO<ChapterDTO, Object[]> getChapters(ChapterPageRequestDTO pageRequestDTO) {
        return null;
    }

    @Override
    public StatusDTO deleteChapter(Long chapterId, String username) {
        return null;
    }

    @Override
    public StatusDTO updateChapter(Long chapterId, ChapterModifyDTO modifyDTO) {
        return null;
    }

}
