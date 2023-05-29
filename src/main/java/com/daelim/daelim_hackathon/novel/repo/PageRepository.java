package com.daelim.daelim_hackathon.novel.repo;

import com.daelim.daelim_hackathon.novel.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page, Long> {

}
