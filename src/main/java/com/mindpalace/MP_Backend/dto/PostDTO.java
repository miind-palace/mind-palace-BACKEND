package com.mindpalace.MP_Backend.dto;

import com.mindpalace.MP_Backend.entity.PostEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private String keyword;
    private String backgroundImage;
    private String[] requestImages;
    private String text; //board contents.
    private String youtubeUrl;

    //글 작성시간, 글 삭제 시간(필요한가?)
    private LocalDateTime createdAt;
//    private LocalDateTime deletedAt;

    //대충 만들어놓은 html 바탕
//    private String postWriter;
//    private String postPass;
//    private String postTitle;
//    private int postHits;

    public PostDTO(Long id, String backgroundImage, String text, String youtubeUrl, LocalDateTime createdAt) {
        this.id = id;
        this.backgroundImage = backgroundImage;
        this.text = text;
        this.youtubeUrl = youtubeUrl;
        this.createdAt = createdAt;
    }

    //Entity -> DTO
    public static PostDTO toPostDTO(PostEntity postEntity){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postEntity.getId());
        postDTO.setKeyword(postEntity.getKeyword());
        postDTO.setBackgroundImage(postEntity.getBackgroundImage());
        postDTO.setRequestImages(postEntity.getRequestImages());
        postDTO.setText(postEntity.getText());
        postDTO.setCreatedAt(postEntity.getCreatedAt());
        postDTO.setYoutubeUrl(postEntity.getYoutubeUrl());
        return postDTO;
    }
}
