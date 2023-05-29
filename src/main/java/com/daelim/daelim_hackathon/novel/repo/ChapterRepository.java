package com.daelim.daelim_hackathon.novel.repo;

import com.daelim.daelim_hackathon.novel.domain.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

}
