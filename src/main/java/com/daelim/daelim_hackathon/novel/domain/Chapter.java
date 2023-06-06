package com.daelim.daelim_hackathon.novel.domain;

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
public class Chapter extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String chapterName;

    @Column
    private int totalPages;

    @ManyToOne(fetch = FetchType.LAZY)
    private Novel novel;

    @OneToOne
    private Chapter prevChapter;
}
