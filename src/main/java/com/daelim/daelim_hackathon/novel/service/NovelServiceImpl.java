package com.daelim.daelim_hackathon.novel.service;

import com.daelim.daelim_hackathon.author.domain.User;
import com.daelim.daelim_hackathon.author.repo.UserRepository;
import com.daelim.daelim_hackathon.novel.domain.Novel;
import com.daelim.daelim_hackathon.novel.dto.novel.NovelDTO;
import com.daelim.daelim_hackathon.novel.dto.novel.PageRequestDTO;
import com.daelim.daelim_hackathon.novel.dto.novel.PageResultDTO;
import com.daelim.daelim_hackathon.novel.dto.novel.StatusDTO;
import com.daelim.daelim_hackathon.novel.repo.ChapterRepository;
import com.daelim.daelim_hackathon.novel.repo.NovelRepository;
import com.daelim.daelim_hackathon.novel.repo.PageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Log4j2
@Service
@RequiredArgsConstructor
public class NovelServiceImpl implements NovelService{
    private final UserRepository userRepository;
    private final NovelRepository novelRepository;
    private final ChapterRepository chapterRepository;
    private final PageRepository pageRepository;

    @Override
    public Long saveNovel(NovelDTO novelDTO) {
        return null;
    }

    @Override
    public StatusDTO getNovel(Long novelId, String username) {
        return null;
    }

    @Override
    public PageResultDTO<NovelDTO, Object[]> getNovels(PageRequestDTO pageRequestDTO) {
        Function<Object[], NovelDTO> fn = (
                entity -> entityToDTO(
                        (Novel)entity[0],
                        (User)entity[1]
                )
        );
        Page<Object[]> result;
        String username = pageRequestDTO.getUsername();
        if (userRepository.existsByUsername(username)) {
            result = novelRepository.getNovelsByHost(
                pageRequestDTO.getPageable(Sort.by("id").descending()),
                userRepository.findByUsername(username).getId()
            );
            return new PageResultDTO<>(result, fn);
        } else {
            throw new RuntimeException("No permission");
        }
    }

    @Override
    public StatusDTO deleteNovel(Long novelId, String username) {
        return null;
    }

    @Override
    public Long modifyNovel(Long novelId, NovelDTO novelDTO) {
        return null;
    }
}
