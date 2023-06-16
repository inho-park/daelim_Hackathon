package com.daelim.daelim_hackathon.page.domain;

import com.daelim.daelim_hackathon.chapter.domain.Chapter;
import com.daelim.daelim_hackathon.common.domain.BooleanToYNConverter;
import com.daelim.daelim_hackathon.common.domain.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Page extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int pageNum;

    @Column(length = 2500)
    private String content;

    @Column(length = 1)
    @Convert(converter = BooleanToYNConverter.class)
    private boolean isImage;

    @ManyToOne(fetch = FetchType.LAZY)
    private Chapter chapter;

    @OneToOne(fetch = FetchType.LAZY)
    private Page prev;
}
