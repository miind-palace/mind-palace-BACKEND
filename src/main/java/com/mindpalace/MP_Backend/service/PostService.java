package com.mindpalace.MP_Backend.service;

import com.mindpalace.MP_Backend.model.dto.PostDTO;
import com.mindpalace.MP_Backend.model.dto.request.PostCreateDTO;
import com.mindpalace.MP_Backend.model.entity.PostEntity;
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

    public void save(PostCreateDTO postCreateDTO) {
        PostEntity postEntity = PostEntity.builder()
                .keyword(postCreateDTO.getKeyword())
                .backgroundImage(postCreateDTO.getBackgroundImage())
                .text(postCreateDTO.getText())
                .videoId(postCreateDTO.getVideoId())
                .memberId(Long.valueOf(postCreateDTO.getMemberId()))
                .build();
        postRepository.save(postEntity);
    }

    public List<PostDTO> findAll() {
        List<PostEntity> postEntityList = postRepository.findAll(); //리포지토리로 가져오면 엔티티로 가져 옴.
        List<PostDTO> postDTOList = new ArrayList<>();
        for (PostEntity postEntity : postEntityList) {
            postDTOList.add(PostDTO.toPostDTO(postEntity));
        }
        return postDTOList;
    }

    public PostDTO findById(Long id) {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(id);
        if (optionalPostEntity.isPresent()) {
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
        int page = pageable.getPageNumber() - 1; //0부터 시작하나 유저에겐 헷갈릴 수 있으므로 -1 처리

        int pageLimit = 4; // 한 페이지에 보여줄 글 갯수
        //Sort.unsorted() 랜덤하게 될지 확인해봐야
        Page<PostEntity> postEntities =
                postRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "createdAt")));
        //Page를 List로 가져가면 getContent() getNumber() getTotalPages() 등 메소드 못 씀.

        //
        //entities에서 post 하나씩 꺼내서 DTO에 담음
        Page<PostDTO> postDTOS = postEntities.map(post -> new PostDTO(post.getId(), post.getBackgroundImage(), post.getText(), post.getVideoId(), post.getCreatedAt()));
        return postDTOS;
    }

    public List<PostDTO> findByMemberId(Long memberId) {
        List<PostEntity> postEntityList = postRepository.findByMemberId(memberId);
        List<PostDTO> postDTOList = new ArrayList<>();

        for (PostEntity postEntity : postEntityList) {
            postDTOList.add(PostDTO.toPostDTO(postEntity));
        }

        return postDTOList;
    }

    public Page<PostDTO> findPageByMemberId(Pageable pageable, Long memberId) {
        Page<PostEntity> postEntityList = postRepository.findPageByMemberId(pageable, memberId);
        Page<PostDTO> postDTOPage = EntityToDtoConverter.convert(postEntityList, pageable);
        return postDTOPage;
    }

    public Page<PostDTO> randomizePosts(Pageable pageable, Long memberId) {
        Page<PostEntity> postEntityList = postRepository.randomizePosts(pageable, memberId);
        Page<PostDTO> postDTORandomPage = EntityToDtoConverter.convert(postEntityList, pageable);

        return postDTORandomPage;
    }
}