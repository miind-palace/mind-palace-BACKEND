package com.mindpalace.MP_Backend.service;

import com.mindpalace.MP_Backend.model.dto.MemberDTO;
import com.mindpalace.MP_Backend.model.dto.request.LoginRequestDTO;
import com.mindpalace.MP_Backend.model.dto.request.SignUpRequestDTO;
import com.mindpalace.MP_Backend.model.dto.response.LoginResponseDTO;
import com.mindpalace.MP_Backend.model.entity.MemberEntity;
import com.mindpalace.MP_Backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void save(SignUpRequestDTO signUpRequestDTO) {
        //1. dto -> entity 변환
        //2. repository의 save 메서드 호출
        // repository의 save 메소드 호출 (조건. entity객체를 넘겨줘야 함)
        MemberEntity memberEntity = MemberEntity.builder()
                .memberEmail(signUpRequestDTO.getMemberEmail())
                .memberPassword(signUpRequestDTO.getMemberPassword())
                .memberName(signUpRequestDTO.getMemberName())
                .build();

        memberRepository.save(memberEntity); // 이 메서드 이름은 save밖에 되지 않는다.
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws AuthenticationException {

        /*
            1. 회원이 입력한 이메일로 DB에서 조회
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
        */
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(loginRequestDTO.getMemberEmail());
        if (byMemberEmail.isPresent()) {
            //조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberEntity.getMemberPassword().equals(loginRequestDTO.getMemberPassword())) {
                // 비밀번호 일치
                // entity -> dto 변환 후 리턴
                System.out.println("비밀번호 일치");
                LoginResponseDTO dto = LoginResponseDTO.builder()
                        .id(memberEntity.getId())
                        .build();
                return dto;
            } else {
                // 비밀번호 불일치(로그인 실패)
                System.out.println("로그인 실패");
                throw new AuthenticationException("잘못된 이메일 혹은 비밀번호를 입력하셨습니다");
            }
        } else {
            //조회 결과가 없다(해당 이메일을 가진 회원 정보가 없다)
            System.out.println("해당 이메일이 존재하지 않는다.");
            throw new AuthenticationException("잘못된 이메일 혹은 비밀번호를 입력하셨습니다");
        }
    }

    public List<MemberDTO> findAll() {
        //리포지토리는 늘 entity로 받는다.
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        //entitylist에서 하나씩 뽑아서 dto list에 담는다.
        for (MemberEntity memberEntity : memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
            //MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            //memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            //optional감싸진 거 get으로 까고 entity객체를 DTO로 변환해서 return
            //MemberEntity memberEntity = optionalMemberEntity.get();
            //MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            //return memberDTO;
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));//id가 없으면 insert쿼리 있으면 update 쿼리 JPA 좋네
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if (byMemberEmail.isPresent()) { //조회 결과가 있으면 누군가 쓰고 있으므로 사용 불가
            return "이미 사용하고 있는 이메일입니다.";
        } else {
            return "사용가능한 이메일입니다!";
        }
    }
}
