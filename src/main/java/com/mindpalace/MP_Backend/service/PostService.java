package com.mindpalace.MP_Backend.service;

import com.mindpalace.MP_Backend.dto.PostDTO;
import com.mindpalace.MP_Backend.entity.PostEntity;
import com.mindpalace.MP_Backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// DTO -> Entity
// Entity -> DTO

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void save(PostDTO postDTO) {
        PostEntity postEntity = PostEntity.toSaveEntity(postDTO);
        postRepository.save(postEntity);
    }

    public List<PostDTO> findAll() {
        List<PostEntity> postEntityList = postRepository.findAll(); //리포지토리로 가져오면 엔티티로 가져 옴.
        List<PostDTO> postDTOList = new ArrayList<>();
        for (PostEntity postEntity: postEntityList){
            postDTOList.add(PostDTO.toPostDTO(postEntity));
        }
        return postDTOList;
    }

    public PostDTO findById(Long id){
        Optional<PostEntity> optionalPostEntity = postRepository.findById(id);
        if (optionalPostEntity.isPresent()){
            PostEntity postEntity = optionalPostEntity.get();
            PostDTO postDTO = PostDTO.toPostDTO(postEntity);
            return postDTO;
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
