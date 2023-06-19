package com.daelim.daelim_hackathon.novel.domain;

import com.daelim.daelim_hackathon.author.domain.User;
import com.daelim.daelim_hackathon.common.domain.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Novel extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private Long love;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeLove(Long result) {
        this.love = result;
    }
}
