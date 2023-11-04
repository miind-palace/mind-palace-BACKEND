package com.mindpalace.MP_Backend.controller;


import com.mindpalace.MP_Backend.exception.ErrorResponse;
import com.mindpalace.MP_Backend.model.dto.EmailCheckDTO;
import com.mindpalace.MP_Backend.model.dto.MemberDTO;
import com.mindpalace.MP_Backend.model.dto.request.LoginRequestDTO;
import com.mindpalace.MP_Backend.model.dto.response.LoginResponseDTO;
import com.mindpalace.MP_Backend.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

import static com.mindpalace.MP_Backend.SessionConst.LOGIN_ID;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = {"회원가입 및 로그인 API"})
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
    @ApiOperation(value = "로그인", response = LoginResponseDTO.class)
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO, HttpSession session
    ) {
        LoginResponseDTO loginResult = memberService.login(loginRequestDTO);
        if (loginResult != null) {
            //로그인 성공
            Long memberId = loginResult.getId();

            //로그인 성공 시 MemberDTO에 담긴 id LoginDTO로 옮김
            LoginResponseDTO loginDTO = new LoginResponseDTO();
            loginDTO.setId(memberId);

            session.setAttribute(LOGIN_ID, loginResult.getId());

            return loginDTO;
        } else {
            //로그인 실패
            return null;
        }
    }


    //로그아웃 요청
    @GetMapping("/member/logout")
    @ApiOperation(value = "로그아웃", response = String.class)
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
    @ApiOperation(value = "회원가입", response = ErrorResponse.class)
    public Map<String, String> save(@RequestBody @Valid MemberDTO memberDTO) {
        return Map.ofEntries();
    }

    //이메일 중복 확인
    @PostMapping("/member/mailCheck")
    @ApiOperation(value = "이메일 중복 확인", response = EmailCheckDTO.class)
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
