package com.daelim.daelim_hackathon.novel.repo;

import com.daelim.daelim_hackathon.author.domain.User;
import com.daelim.daelim_hackathon.author.repo.UserRepository;
import com.daelim.daelim_hackathon.chapter.domain.Chapter;
import com.daelim.daelim_hackathon.chapter.repo.ChapterRepository;
import com.daelim.daelim_hackathon.novel.domain.Novel;
import com.daelim.daelim_hackathon.page.domain.Page;
import com.daelim.daelim_hackathon.page.repo.PageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NovelRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NovelRepository novelRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private PageRepository pageRepository;

    /**
     * 시작 시 위에서 순서대로 실행하기
     */
    @Test
    public void 소설_생성() {
        User author;
        for (int i = 1; i < 11; i++) {
            author = userRepository.findByUsername("username" + i).get();
            for (int j = 1; j < 11; j++) {
                novelRepository.save(
                        Novel.builder()
                                .author(author)
                                .love(0l)
                                .title("author" + i + "  title" + j)
                                .build()
                );
            }
        }
    }

    @Test
    public void 챕터_생성() {
        Novel novel;
        for (long i = 1; i < 11; i++) {
            novel = novelRepository.getReferenceById(i);
            for (int j = 1; j < 11; j++) {
                chapterRepository.save(
                        Chapter.builder()
                                .chapterName("novel" + i + "  chapter" + j)
                                .totalPages(j)
                                .novel(novel)
                                .build()
                );
            }
        }
    }

    @Test
    public void 이전_챕터연결() {
        for (long i = 2; i < 11; i++) {
            Chapter chapter = chapterRepository.findById(i).orElse(null);
            System.out.println(chapter);
            chapter.changePrevChapter(i - 1);
            chapterRepository.save(chapter);
        }
    }

    @Test
    public void 페이지_생성() {
        Chapter chapter;
        for (long i = 1; i < 11; i++) {
            chapter = chapterRepository.getReferenceById(i);
            pageRepository.save(
                    Page.builder()
                            .isImage(true)
                            .chapter(chapter)
                            .build()
            );
            for (int j = 2; j < 11; j++) {
                pageRepository.save(
                        Page.builder()
                                .isImage(false)
                                .content("chapter" + i + "  page" + j)
                                .chapter(chapter)
                                .build()
                );
            }
        }
    }

    @Test
    public void 이전_페이지연결() {
        Page prev;
        int result [] = new int [9];
        for (long i = 2; i < 11; i++) {
            prev = pageRepository.findById(i - 1).orElse(null);
            result[(int) (i-2)] = pageRepository.modifyPrevPage(i, prev);
        }
    }
}
