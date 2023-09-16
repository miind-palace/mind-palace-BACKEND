package com.mindpalace.MP_Backend.repository;

import com.mindpalace.MP_Backend.dto.PostDTO;
import com.mindpalace.MP_Backend.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {//Entity, PK의 타입
    @Query("select p from PostEntity p where memberId=:memberId")
    List<PostEntity> findByMemberId(@Param("memberId") Long memberId);

    @Query("select p from PostEntity p where memberId=:memberId")
    Page<PostEntity> findPageByMemberId(Pageable paging, @Param("memberId") Long memberId);

    @Query("select p from PostEntity p order by rand()")
    Page<PostEntity> randomizePosts(Pageable pageable);
}