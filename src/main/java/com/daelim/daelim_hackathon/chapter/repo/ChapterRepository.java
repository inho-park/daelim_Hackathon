package com.daelim.daelim_hackathon.chapter.repo;

import com.daelim.daelim_hackathon.chapter.domain.Chapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    Optional<Chapter> findByPrevChapter(Long prevId);

    @Query(
            "SELECT c " +
            "FROM Chapter c " +
            "WHERE c.novel.id=:id"
    )
    Page<Object[]> getChaptersByNovel(Pageable pageable, @Param("id") Long id);
}
