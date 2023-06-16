package com.daelim.daelim_hackathon.page.repo;

import com.daelim.daelim_hackathon.page.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PageRepository extends JpaRepository<Page, Long> {
    @Transactional
    @Modifying
    @Query(
            "UPDATE Page p " +
            "SET p.prev =:prev " +
            "WHERE p.id =:pageId "
    )
    int modifyPrevPage(@Param("pageId") Long pageId , @Param("prev") Page prev);
}
