package com.mindpalace.MP_Backend.controller;

import com.mindpalace.MP_Backend.dto.MemberDTO;
import com.mindpalace.MP_Backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            //로그인 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "member/main";
        } else {
            //로그인 실패
            return "member/login";
        }
    }

    //로그아웃 요청
    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 로그인 무효화
        return "index";
    }

    // 회원가입
    //페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm() {
        return "save";
    }

    //회원가입 요청
    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "member/login";
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
