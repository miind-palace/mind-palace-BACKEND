package com.mindpalace.MP_Backend.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindpalace.MP_Backend.LocalDateTimeSerializer;
import com.mindpalace.MP_Backend.dto.MemberDTO;
import com.mindpalace.MP_Backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;

import static com.mindpalace.MP_Backend.SessionConst.LOGIN_EMAIL;

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
    public String login(@RequestBody MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            //로그인 성공
            Long id = loginResult.getId();
            MemberDTO memberId = new MemberDTO();
            memberId.setId(id);
            session.setAttribute(LOGIN_EMAIL, loginResult.getMemberEmail());
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
            Gson gson = gsonBuilder.setPrettyPrinting().create();

            String json = gson.toJson(memberId);
            // 로그인 성공
            return json;
        } else {
            //로그인 실패
            return "로그인 실패";
        }
    }

    //로그아웃 요청
    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 로그인 무효화
        return "로그아웃 성공";
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
        System.out.println("MemberController.save");
        System.out.println("memberDTO = " + memberDTO);
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
