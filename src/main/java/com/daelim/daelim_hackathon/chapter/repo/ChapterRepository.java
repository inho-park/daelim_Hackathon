package com.daelim.daelim_hackathon.chapter.repo;

import com.daelim.daelim_hackathon.chapter.domain.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    @Transactional
    @Modifying
    @Query(
            "UPDATE Chapter c " +
            "SET c.prevChapter =:prevChapter " +
            "WHERE c.id =:chapterId "
    )
    int modifyPrevChapter(@Param("chapterId") Long chapterId ,@Param("prevChapter") Chapter prevChapter);

    Optional<Chapter> findByPrevChapter_Id(Long prevId);
}
