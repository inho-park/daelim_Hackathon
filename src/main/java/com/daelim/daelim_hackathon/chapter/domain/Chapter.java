package com.daelim.daelim_hackathon.chapter.domain;

import com.daelim.daelim_hackathon.common.domain.BaseTimeEntity;
import com.daelim.daelim_hackathon.novel.domain.Novel;
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

    public void changeChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}
