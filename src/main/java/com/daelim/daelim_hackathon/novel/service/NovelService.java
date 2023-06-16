package com.daelim.daelim_hackathon.novel.service;

import com.daelim.daelim_hackathon.author.domain.User;
import com.daelim.daelim_hackathon.common.dto.PageRequestDTO;
import com.daelim.daelim_hackathon.common.dto.PageResultDTO;
import com.daelim.daelim_hackathon.novel.domain.Novel;
import com.daelim.daelim_hackathon.novel.dto.ModifyDTO;
import com.daelim.daelim_hackathon.novel.dto.NovelDTO;
import com.daelim.daelim_hackathon.common.dto.StatusDTO;

public interface NovelService {
    StatusDTO saveNovel(NovelDTO novelDTO);
    NovelDTO getNovel(Long novelId, String username);
    PageResultDTO<NovelDTO, Object[]> getNovels(PageRequestDTO pageRequestDTO);
    StatusDTO deleteNovel(Long novelId, String username);
    StatusDTO updateNovel(Long novelId, ModifyDTO modifyDTO);

    default Novel dtoToEntity(NovelDTO dto, User author) {
        Novel novel = Novel.builder()
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
                .username(author.getUsername())
                .build();
        return novelDTO;
    }
}
