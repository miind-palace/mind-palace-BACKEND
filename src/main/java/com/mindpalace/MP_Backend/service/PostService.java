package com.mindpalace.MP_Backend.service;

import com.mindpalace.MP_Backend.dto.PostDTO;
import com.mindpalace.MP_Backend.entity.PostEntity;
import com.mindpalace.MP_Backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<PostDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber()-1; //0부터 시작하나 유저에겐 헷갈릴 수 있으므로 -1 처리
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
        //Sort.unsorted() 랜덤하게 될지 확인해봐야
        Page<PostEntity> postEntities =
                postRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        //Page를 List로 가져가면 getContent() getNumber() getTotalPages() 등 메소드 못 씀.

        //
        //entities에서 post 하나씩 꺼내서 DTO에 담음
        Page<PostDTO> postDTOS = postEntities.map(post -> new PostDTO(post.getId(), post.getBackgroundImage(), post.getText(), post.getVideoId(), post.getCreatedAt()));
        return postDTOS;
    }
}
