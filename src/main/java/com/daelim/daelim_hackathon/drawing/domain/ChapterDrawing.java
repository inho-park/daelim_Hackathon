package com.daelim.daelim_hackathon.drawing.domain;

import com.daelim.daelim_hackathon.chapter.domain.Chapter;
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
public class ChapterDrawing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String uuid;

    @ManyToOne
    private Chapter chapter;
}
