package com.daelim.daelim_hackathon.chapter.service;

import com.daelim.daelim_hackathon.chapter.domain.Chapter;
import com.daelim.daelim_hackathon.chapter.dto.ChapterDTO;
import com.daelim.daelim_hackathon.chapter.dto.ChapterModifyDTO;
import com.daelim.daelim_hackathon.chapter.dto.ChapterPageRequestDTO;
import com.daelim.daelim_hackathon.chapter.exception.ChapterException;
import com.daelim.daelim_hackathon.chapter.exception.LastChapterException;
import com.daelim.daelim_hackathon.chapter.repo.ChapterRepository;
import com.daelim.daelim_hackathon.common.dto.PageResultDTO;
import com.daelim.daelim_hackathon.common.dto.StatusDTO;
import com.daelim.daelim_hackathon.novel.domain.Novel;
import com.daelim.daelim_hackathon.novel.repo.NovelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Log4j2
@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService{

    private final ChapterRepository chapterRepository;
    private final NovelRepository novelRepository;

    @Override
    public StatusDTO saveChapter(ChapterDTO chapterDTO) {
        try {
            Novel novel = novelRepository.findById(chapterDTO.getNovelId()).get();
            entityToDTO(chapterRepository.save(dtoToEntity(chapterDTO)), novel);
            return StatusDTO.builder().status("success").build();
        } catch(Exception e) {
            throw e;
        }
    }

    @Override
    public ChapterDTO getChapter(Long chapterId) {
        Optional<Chapter> optional = chapterRepository.findById(chapterId);
        Chapter chapter;
        if (optional.isPresent()) {
            chapter = optional.get();
            return entityToDTO(optional.get(), chapter.getNovel());
        } else {
            throw new ChapterException("chapter isn't exist");
        }
    }

    @Override
    public ChapterDTO getNextChapter(Long prevId) {
        Optional<Chapter> optional = chapterRepository.findByPrevChapter(prevId);
        Chapter chapter;
        if (optional.isPresent()) {
            chapter = optional.get();
            return entityToDTO(chapter, chapter.getNovel());
        } else {
            throw new LastChapterException("next chapter isn't exist");
        }
    }

    @Override
    public String getFileName(Long chapterId) {
        return null;
    }

    @Override
    public PageResultDTO<ChapterDTO, Object[]> getChapters(ChapterPageRequestDTO pageRequestDTO) {
        Function<Object[], ChapterDTO> fn = (
                entity -> entityToDTO(
                        (Chapter) entity[0],
                        (Novel) entity[1]
                )
        );
        Page<Object[]> result = chapterRepository.getChaptersByNovel_Id(
                pageRequestDTO.getPageable(Sort.by("id").descending()),
                Long.parseLong(pageRequestDTO.getNovelId())
        );

        return new PageResultDTO<>(result, fn);
    }

    @Transactional
    @Override
    public StatusDTO deleteChapter(Long chapterId) {

        // 다음 챕터가 있다면 해당 챕터의 이전챕터와 이어줘야함
        Optional<Chapter> nextOptional = chapterRepository.findByPrevChapter(chapterId);
        if(nextOptional.isPresent()) {
            Chapter next = nextOptional.get();
            Long prev = chapterRepository.getReferenceById(chapterId).getPrevChapter();
            chapterRepository.delete(chapterRepository.getReferenceById(chapterId));
            next.changePrevChapter(prev);
            chapterRepository.save(next);
        } else {
            chapterRepository.delete(chapterRepository.getReferenceById(chapterId));
        }
        return StatusDTO.builder().status("success").build();
    }

    @Override
    public StatusDTO updateChapter(Long chapterId, ChapterModifyDTO modifyDTO) {
        Optional<Chapter> optional = chapterRepository.findById(chapterId);
        if(optional.isPresent()) {
            Chapter chapter = optional.get();
            chapter.changeChapterName(modifyDTO.getChapterName());
            chapterRepository.save(chapter);
            return StatusDTO.builder().status("success").build();
        } else {
            throw new ChapterException("해당 id 에 속하는 챕터가 없음");
        }
    }

}
