package com.daelim.daelim_hackathon.novel.service;

import com.daelim.daelim_hackathon.author.domain.User;
import com.daelim.daelim_hackathon.author.repo.UserRepository;
import com.daelim.daelim_hackathon.novel.domain.Novel;
import com.daelim.daelim_hackathon.novel.dto.novel.NovelDTO;
import com.daelim.daelim_hackathon.novel.dto.novel.PageRequestDTO;
import com.daelim.daelim_hackathon.novel.dto.novel.PageResultDTO;
import com.daelim.daelim_hackathon.novel.dto.novel.StatusDTO;
import com.daelim.daelim_hackathon.novel.repo.NovelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Log4j2
@Service
@RequiredArgsConstructor
public class NovelServiceImpl implements NovelService{
    private final UserRepository userRepository;
    private final NovelRepository novelRepository;

    @Override
    public StatusDTO saveNovel(NovelDTO novelDTO) {
        Optional<User> option = userRepository.findByUsername(novelDTO.getHostUsername());
        if(option.isPresent()) {
            User host = option.get();
            Novel novel = dtoTOEntity(novelDTO, host);
            novelRepository.save(novel);
            StatusDTO statusDTO = StatusDTO.builder().status("success").build();
            return statusDTO;
        } else {
            throw new RuntimeException("This account doesn't exist");
        }
    }

    @Override
    public NovelDTO getNovel(Long novelId, String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<Novel> novelOptional = novelRepository.findById(novelId);
        User author;
        Novel novel;
        if (userOptional.isPresent()) {
            author = userOptional.get();
        } else {
            throw new RuntimeException("This account doesn't exist");
        }
        if (novelOptional.isPresent()) {
            novel = novelOptional.get();
        } else {
            throw new RuntimeException("Not found this novel");
        }

        return entityToDTO(novel, author);
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
                userRepository.findByUsername(username).get().getId()
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
    public StatusDTO modifyNovel(Long novelId, NovelDTO novelDTO) {
        return null;
    }
}
