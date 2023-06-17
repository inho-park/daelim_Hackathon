package com.daelim.daelim_hackathon.novel.service;

import com.daelim.daelim_hackathon.author.domain.User;
import com.daelim.daelim_hackathon.novel.dto.NovelPageRequestDTO;
import com.daelim.daelim_hackathon.common.config.dto.PageResultDTO;
import com.daelim.daelim_hackathon.novel.domain.Novel;
import com.daelim.daelim_hackathon.novel.dto.NovelModifyDTO;
import com.daelim.daelim_hackathon.novel.dto.NovelDTO;
import com.daelim.daelim_hackathon.common.config.dto.StatusDTO;

public interface NovelService {
    StatusDTO saveNovel(NovelDTO novelDTO);
    NovelDTO getNovel(Long novelId, String username);
    PageResultDTO<NovelDTO, Object[]> getNovels(NovelPageRequestDTO pageRequestDTO);
    StatusDTO deleteNovel(Long novelId, String username);
    StatusDTO updateNovel(Long novelId, NovelModifyDTO modifyDTO);

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
