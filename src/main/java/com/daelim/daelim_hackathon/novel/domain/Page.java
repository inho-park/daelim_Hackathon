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
public class Page extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int pageNum;

    @ManyToOne(fetch = FetchType.LAZY)
    private Chapter chapter;

    @Column(length = 2500)
    private String content;
}
