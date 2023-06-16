package com.daelim.daelim_hackathon.novel.repo;

import com.daelim.daelim_hackathon.novel.domain.Novel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface NovelRepository extends JpaRepository<Novel, Long> {

    @Query(
            "SELECT n, n.author " +
            "FROM Novel n " +
            "WHERE n.author.id=:id"
    )
    Page<Object[]> getNovelsByAuthor(Pageable pageable, @Param("id") Long id);

}
