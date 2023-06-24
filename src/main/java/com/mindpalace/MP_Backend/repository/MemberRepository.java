package com.mindpalace.MP_Backend.repository;

import com.mindpalace.MP_Backend.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> { //entity와 pk가 어떤 타입인지
    // 이메일로 회원 정보 조회 (select * from member_table where member_email='')
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}

