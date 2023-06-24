package com.mindpalace.MP_Backend.repository;

import com.mindpalace.MP_Backend.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {//Entity, PK의 타입
}
