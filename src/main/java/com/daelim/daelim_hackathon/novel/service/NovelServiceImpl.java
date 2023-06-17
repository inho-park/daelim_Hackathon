package com.daelim.daelim_hackathon.novel.service;

import com.daelim.daelim_hackathon.author.domain.User;
import com.daelim.daelim_hackathon.author.repo.UserRepository;
import com.daelim.daelim_hackathon.drawing.repo.NovelDrawingRepository;
import com.daelim.daelim_hackathon.novel.dto.NovelPageRequestDTO;
import com.daelim.daelim_hackathon.common.dto.PageResultDTO;
import com.daelim.daelim_hackathon.novel.domain.Novel;
import com.daelim.daelim_hackathon.novel.dto.NovelModifyDTO;
import com.daelim.daelim_hackathon.novel.dto.NovelDTO;
import com.daelim.daelim_hackathon.common.dto.StatusDTO;
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
    private final NovelDrawingRepository novelDrawingRepository;

    @Override
    public StatusDTO saveNovel(NovelDTO novelDTO) {
        Optional<User> option = userRepository.findByUsername(novelDTO.getUsername());
        if(option.isPresent()) {
            Novel novel = dtoToEntity(novelDTO, option.get());
            novelRepository.save(novel);
            return StatusDTO.builder().status("success").build();
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
        if (author.getUsername().equals(novel.getAuthor().getUsername())) {
            return entityToDTO(novel, author);
        } else {
            throw new RuntimeException("this account doesn't matched novels");
        }
    }

    @Override
    public PageResultDTO<NovelDTO, Object[]> getNovels(NovelPageRequestDTO pageRequestDTO) {
        Function<Object[], NovelDTO> fn = (
                entity -> entityToDTO(
                        (Novel)entity[0],
                        (User)entity[1]
                )
        );
        Page<Object[]> result;
        String username = pageRequestDTO.getUsername();
        if (userRepository.existsByUsername(username)) {
            result = novelRepository.getNovelsByAuthor(
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
        Optional<User> option = userRepository.findByUsername(username);
        if(option.isPresent()) {
            novelRepository.delete(
                    Novel.builder()
                            .id(novelId)
                            .author(option.get())
                            .build()
            );
            return StatusDTO.builder().status("success").build();
        } else {
            throw new RuntimeException("This account doesn't exist");
        }
    }

    @Override
    public StatusDTO updateNovel(Long novelId, NovelModifyDTO modifyDTO) {
        try {
            Novel novel = novelRepository.getReferenceById(novelId);
            novel.changeTitle(modifyDTO.getTitle());
            novelRepository.save(novel);
            return StatusDTO.builder().status("success").build();
        } catch(Exception e) {
            throw e;
        }
    }

    @Override
    public StatusDTO love(Long novelId) {
        try {
            Novel novel = novelRepository.getReferenceById(novelId);
            Long result = novel.getLove() + 1;
            novel.addLove(result);
            novelRepository.save(novel);
            return StatusDTO.builder().status("success").build();
        } catch(Exception e) {
            throw e;
        }

    }

    @Override
    public String getFileName(Long novelId) {
        return novelDrawingRepository.findByNovel_Id(novelId).getUuid();
    }

    @Override
    public String deleteFile(Long novelId) {
        String uuid = novelDrawingRepository.findByNovel_Id(novelId).getUuid();
        novelDrawingRepository.deleteNovelDrawingByNovel_Id(novelId);
        return uuid;
    }
}
