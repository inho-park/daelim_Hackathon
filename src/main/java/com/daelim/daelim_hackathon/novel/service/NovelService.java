package com.daelim.daelim_hackathon.novel.service;

import com.daelim.daelim_hackathon.author.domain.User;
import com.daelim.daelim_hackathon.novel.domain.Novel;
import com.daelim.daelim_hackathon.novel.dto.novel.NovelDTO;
import com.daelim.daelim_hackathon.novel.dto.novel.PageRequestDTO;
import com.daelim.daelim_hackathon.novel.dto.novel.PageResultDTO;
import com.daelim.daelim_hackathon.novel.dto.novel.StatusDTO;

public interface NovelService {
    StatusDTO saveNovel(NovelDTO novelDTO);
    NovelDTO getNovel(Long novelId, String username);
    PageResultDTO<NovelDTO, Object[]> getNovels(PageRequestDTO pageRequestDTO);
    StatusDTO deleteNovel(Long novelId, String username);
    StatusDTO modifyNovel(Long novelId, NovelDTO novelDTO);

    default Novel dtoTOEntity(NovelDTO dto, User host) {
        Novel novel = Novel.builder()
                .id(dto.getNovelId())
                .title(dto.getTitle())
                .host(host)
                .build();
        return novel;
    }

    default NovelDTO entityToDTO(Novel novel, User host) {
        NovelDTO novelDTO = NovelDTO.builder()
                .novelId(novel.getId())
                .regDate(novel.getRegDate())
                .modDate(novel.getModDate())
                .title(novel.getTitle())
                .hostUsername(host.getUsername())
                .build();
        return novelDTO;
    }
}
