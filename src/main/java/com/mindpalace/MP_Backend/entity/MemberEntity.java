package com.mindpalace.MP_Backend.entity;

import com.mindpalace.MP_Backend.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Setter
@Getter
@Table(name = "mp_member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 지정
    private Long id;

    @Column(unique = true) // unique 제약조건 추가
    @NotBlank(message = "이메일은 필수 입력 값입니다")
    @Email(message = "이메일 형식에 맞지 않습니다")
    private String memberEmail;

    @Column
    @NotBlank(message = "비밀번호는 필수 입력 값입니다")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).*$",
            message = "비밀번호는 영문, 숫자, 특수문자를 혼용해야 합니다"
    )
    private String memberPassword;

    @Column
    @NotBlank(message = "이름은 필수 입력 값입니다")
    private String memberName;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        return memberEntity;
    }
}