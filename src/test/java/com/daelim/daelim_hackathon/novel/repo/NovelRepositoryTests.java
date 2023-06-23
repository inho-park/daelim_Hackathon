package com.daelim.daelim_hackathon.novel.repo;

import com.daelim.daelim_hackathon.author.domain.User;
import com.daelim.daelim_hackathon.author.repo.UserRepository;
import com.daelim.daelim_hackathon.chapter.domain.Chapter;
import com.daelim.daelim_hackathon.chapter.repo.ChapterRepository;
import com.daelim.daelim_hackathon.novel.domain.Novel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
public class NovelRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NovelRepository novelRepository;
    @Autowired
    private ChapterRepository chapterRepository;

    /**
     * 시작 시 위에서 순서대로 실행하기
     */
    @Test
    public void 소설_생성() {
        String [] genre = {"공포", "쓰릴러", "추리", "코미디", "과학", "판타지", "무협", "로맨스"};
        User author;
        for (int i = 1; i < 11; i++) {
            author = userRepository.findByUsername("username" + i).orElse(null);
            for (int j = 1; j < 11; j++) {
                if (j%2 == 0) novelRepository.save(
                        Novel.builder()
                                .author(author)
                                .isPublic(true)
                                .love(0L)
                                .genre(genre[(int) (Math.random() * 7)])
                                .title("author" + i + "  title" + j)
                                .build()
                );
                else novelRepository.save(
                        Novel.builder()
                                .author(author)
                                .isPublic(false)
                                .love(0L)
                                .genre(genre[(int) (Math.random() * 7)])
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
            long x = 1;
            novel = novelRepository.getReferenceById(i);
            for (long j = 1; j < 11; j++) {
                if (i == 1 && j >= 2) {
                    chapterRepository.save(
                            Chapter.builder()
                                    .chapterName("novel" + i + "  chapter" + j)
                                    .prevChapter(x - 1)
                                    .novel(novel)
                                    .build()
                    );
                }
                else {
                    chapterRepository.save(
                            Chapter.builder()
                                    .chapterName("novel" + i + "  chapter" + j)
                                    .novel(novel)
                                    .build()
                    );
                }

                x++;
            }
        }
    }
}
