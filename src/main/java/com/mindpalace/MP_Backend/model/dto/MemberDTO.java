package com.mindpalace.MP_Backend.model.dto;

import com.mindpalace.MP_Backend.model.entity.MemberEntity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private Long id;

    @NotBlank(message = "이메일은 필수 입력 값입니다")
    @Email(message = "이메일 형식에 맞지 않습니다")
    private String memberEmail;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).*$",
            message = "비밀번호는 영문, 숫자, 특수문자를 혼용해야 합니다"
    )
    private String memberPassword;

    @NotBlank(message = "이름은 필수 입력 값입니다")
    private String memberName;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        return memberDTO;
    }
}
