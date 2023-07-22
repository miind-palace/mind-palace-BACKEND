package com.mindpalace.MP_Backend.entity;

import com.mindpalace.MP_Backend.dto.PostDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name="mp_post")
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
    private Long memberId;

    public static PostEntity toSaveEntity(PostDTO postDTO){
        PostEntity postEntity = new PostEntity();
        postEntity.setKeyword(postDTO.getKeyword());
        postEntity.setBackgroundImage(postDTO.getBackgroundImage());
        postEntity.setRequestImages(postDTO.getRequestImages());
        postEntity.setText(postDTO.getText());
        postEntity.setVideoId(postDTO.getVideoId());
        postEntity.setMemberId(postDTO.getMemberId());
        return postEntity;
    }
}