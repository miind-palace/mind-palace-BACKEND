package com.mindpalace.MP_Backend.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "mp_post")
public class PostEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String keyword;

    @Column
    @NotNull //Notnull
    private String backgroundImage;

    @Column
    private String[] requestImages;

    @Column(length = 500) //500자 제한
    private String text;

    @Column //BGM 대신 유튜브 url
    private String videoId;

    @Column
    private int fileAttached; // 1 or 0

    @Column
    @NotNull(message = "멤버 ID는 필수 입력 값입니다")
    private Long memberId;

    @Builder
    public PostEntity(Long id, String keyword, String backgroundImage, String[] requestImages, String text, String videoId, int fileAttached, Long memberId) {
        this.id = id;
        this.keyword = keyword;
        this.backgroundImage = backgroundImage;
        this.requestImages = requestImages;
        this.text = text;
        this.videoId = videoId;
        this.fileAttached = fileAttached;
        this.memberId = memberId;
    }
}