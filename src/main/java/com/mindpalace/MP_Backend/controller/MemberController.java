package com.mindpalace.MP_Backend.controller;

import com.mindpalace.MP_Backend.dto.MemberDTO;
import com.mindpalace.MP_Backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.mindpalace.MP_Backend.SessionConst.LOGIN_EMAIL;

@Slf4j
@Controller
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
    public MemberDTO login(@ModelAttribute MemberDTO memberDTO,
                           HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            //로그인 성공
            Long id = loginResult.getId();
            MemberDTO memberId = new MemberDTO();
            memberId.setId(id);
            session.setAttribute(LOGIN_EMAIL, loginResult.getMemberEmail());

            session.getAttributeNames().asIterator()
                    .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

            // 로그인 성공
            log.info("MemberController {}" + memberId);
            return memberId;
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
    public ResponseEntity<String> save(@RequestBody MemberDTO memberDTO) {
        try {
            memberService.save(memberDTO);
            return ResponseEntity.ok("회원가입 성공!");
        } catch (Exception e) {
            String errorMessage = "회원가입 실패: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    //아이디 중복 확인
    @PostMapping("/member/mailCheck")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
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
