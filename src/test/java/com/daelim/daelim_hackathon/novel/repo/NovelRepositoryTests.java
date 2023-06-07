package com.daelim.daelim_hackathon.novel.repo;

import com.daelim.daelim_hackathon.author.domain.User;
import com.daelim.daelim_hackathon.author.repo.UserRepository;
import com.daelim.daelim_hackathon.novel.domain.Chapter;
import com.daelim.daelim_hackathon.novel.domain.Novel;
import com.daelim.daelim_hackathon.novel.domain.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

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

    @Test
    public void 소설_생성() {
        User author;
        for (int i = 1; i < 11; i++) {
            author = userRepository.findByUsername("username" + i).get();
            novelRepository.save(
                    Novel.builder()
                            .author(author)
                            .title("title" + i)
                            .build()
            );
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
                                .chapterName("chapter" + j)
                                .totalPages(j)
                                .novel(novel)
                                .build()
                );
            }
        }
    }

    @Test
    public void 이전_챕터연결() {
        Chapter prevChapter;
        int result [] = new int [9];
        for (long i = 2; i < 11; i++) {
            prevChapter = chapterRepository.getReferenceById(i - 1);
            result[(int) (i-2)] = chapterRepository.modifyPrevChapter(i, prevChapter);
        }

        for (int id : result) System.out.println(id);
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
                                .content("content" + i)
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
            prev = pageRepository.getReferenceById(i - 1);
            result[(int) (i-2)] = pageRepository.modifyPrevPage(i, prev);
        }

        for (int id : result) System.out.println(id);
    }
}
