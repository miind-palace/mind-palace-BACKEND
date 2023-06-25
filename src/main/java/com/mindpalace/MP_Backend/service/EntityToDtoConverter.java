package com.mindpalace.MP_Backend;

import com.mindpalace.MP_Backend.dto.PostDTO;
import com.mindpalace.MP_Backend.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class EntityToDtoConverter {
    public static Page<PostDTO> convert(Page<PostEntity> entityPage, Pageable pageable) {
        List<PostDTO> dtoList = entityPage.getContent()
                .stream()
                .map(EntityToDtoConverter::convertToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    private static PostDTO convertToDto(PostEntity entity) {
        return PostDTO.toPostDTO(entity);
    }
}