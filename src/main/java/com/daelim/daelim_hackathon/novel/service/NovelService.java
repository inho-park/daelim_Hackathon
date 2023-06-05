package com.daelim.daelim_hackathon.novel.service;

import com.daelim.daelim_hackathon.author.domain.User;
import com.daelim.daelim_hackathon.novel.domain.Novel;
import com.daelim.daelim_hackathon.novel.dto.novel.*;

public interface NovelService {
    StatusDTO saveNovel(NovelDTO novelDTO);
    NovelDTO getNovel(Long novelId, String username);
    PageResultDTO<NovelDTO, Object[]> getNovels(PageRequestDTO pageRequestDTO);
    StatusDTO deleteNovel(Long novelId, String username);
    StatusDTO modifyNovel(Long novelId, ModifyDTO modifyDTO);

    default Novel dtoTOEntity(NovelDTO dto, User author) {
        Novel novel = Novel.builder()
                .id(dto.getNovelId())
                .title(dto.getTitle())
                .author(author)
                .build();
        return novel;
    }

    default NovelDTO entityToDTO(Novel novel, User author) {
        NovelDTO novelDTO = NovelDTO.builder()
                .novelId(novel.getId())
                .regDate(novel.getRegDate())
                .modDate(novel.getModDate())
                .title(novel.getTitle())
                .hostUsername(author.getUsername())
                .build();
        return novelDTO;
    }
}
