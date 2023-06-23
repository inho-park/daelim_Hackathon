package com.daelim.daelim_hackathon.drawing.repo;

import com.daelim.daelim_hackathon.drawing.domain.ChapterDrawing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterDrawingRepository extends JpaRepository<ChapterDrawing, Long> {
    ChapterDrawing findByChapter_Id(Long chapterId);

    int deleteChapterDrawingByChapter_Id(Long chapterId);
}
