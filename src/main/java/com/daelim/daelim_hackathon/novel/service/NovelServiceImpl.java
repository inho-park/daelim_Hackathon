package com.daelim.daelim_hackathon.novel.service;

import com.daelim.daelim_hackathon.author.domain.User;
import com.daelim.daelim_hackathon.author.repo.UserRepository;
import com.daelim.daelim_hackathon.common.dto.PageRequestDTO;
import com.daelim.daelim_hackathon.common.dto.PageResultDTO;
import com.daelim.daelim_hackathon.novel.domain.Novel;
import com.daelim.daelim_hackathon.novel.dto.ModifyDTO;
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
    public StatusDTO updateNovel(Long novelId, ModifyDTO modifyDTO) {
        Optional<User> userOptional = userRepository.findByUsername(modifyDTO.getUsername());
        if(userOptional.isPresent()) {
            Novel novel = novelRepository.getReferenceById(novelId);
            novel.changeTitle(modifyDTO.getTitle());
            novelRepository.save(novel);
            return StatusDTO.builder().status("success").build();
        } else {
            throw new RuntimeException("This account doesn't exist");
        }
    }
}
