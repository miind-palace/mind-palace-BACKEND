package com.mindpalace.MP_Backend.controller;


import com.mindpalace.MP_Backend.dto.LoginDTO;
import com.mindpalace.MP_Backend.dto.EmailCheckDTO;
import com.mindpalace.MP_Backend.dto.MemberDTO;
import com.mindpalace.MP_Backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mindpalace.MP_Backend.SessionConst.LOGIN_EMAIL;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;

    //로그인
    //로그인 페이지 요청
    @GetMapping("/member/login")
    public String loginForm() {
        return "member/login";
    }

    //로그인 요청
    @PostMapping("/member/login")
    public LoginDTO login(@ModelAttribute MemberDTO memberDTO,
                          HttpSession session
                          ) {

        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            //로그인 성공
            Long memberId = loginResult.getId();

            //로그인 성공 시 MemberDTO에 담긴 id LoginDTO로 옮김
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setId(memberId);

            session.setAttribute(LOGIN_EMAIL, loginResult.getMemberEmail());

            session.getAttributeNames().asIterator()
                    .forEachRemaining(name-> log.info("session name={}, value={}", name, session.getAttribute(name)));

            return loginDTO;
        } else {
            //로그인 실패
            return null;
        }
    }


    //로그아웃 요청
    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 로그인 무효화
        return "redirect:/";
    }

    // 회원가입
    //페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm() {
        return "member/save";
    }

    //회원가입 요청
    @PostMapping("/member/save")
    public ResponseEntity<String> save(@Validated
                                       @RequestBody MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);

        try {
            memberService.save(memberDTO);
            return ResponseEntity.ok("회원가입 성공!");
        } catch (Exception e) {
            String exceptionMessage = "회원가입 실패: " + e.getMessage();
            List<String> errorMessages = extractErrorMessages(exceptionMessage);
            String errorMessage = String.join(", ", errorMessages);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    //에러 메시지 추출 메서드
    private List<String> extractErrorMessages(String exceptionMessage) {
        List<String> errorMessages = new ArrayList<>();

        Pattern pattern = Pattern.compile("messageTemplate='(.*?)'");
        Matcher matcher = pattern.matcher(exceptionMessage);

        while (matcher.find()) {
            errorMessages.add(matcher.group(1));
        }

        return errorMessages;
    }

    //아이디 중복 확인
    @PostMapping("/member/mailCheck")
    public @ResponseBody EmailCheckDTO emailCheck(@RequestParam("memberEmail") String memberEmail) {
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        EmailCheckDTO emailCheckDTO = new EmailCheckDTO();
        emailCheckDTO.setMessage(checkResult);

        emailCheckDTO.setDuplicated(checkResult.equals("이미 사용하고 있는 이메일입니다."));//중복이면 true 아니면 false

        return emailCheckDTO;
    }


//    //회원 정보 수정
//    @GetMapping("/times/member/update")
//    public String updateForm(HttpSession session, Model model) {
//        String myEmail = (String) session.getAttribute("loginEmail");
//        MemberDTO memberDTO = memberService.updateForm(myEmail);
//        model.addAttribute("updateMember", memberDTO);
//        return "update";
//    }
//
//    @PostMapping("/times/member/update")
//    public String update(@ModelAttribute MemberDTO memberDTO) {
//        memberService.update(memberDTO);
//        return "redirect:/times/member/" + memberDTO.getId();
//    }
//
//    //회원 정보 삭제
//    @GetMapping("/times/member/delete/{id}")
//    public String deleteById(@PathVariable Long id) {
//        memberService.deleteById(id);
//        //model에 담아서 가줘야하는데 안 담아가니 redirect로 findAll 불러와야
//        return "redirect:/times/member/";
//    }
}
