package com.mindpalace.MP_Backend.controller;

import com.mindpalace.MP_Backend.SessionConst;
import com.mindpalace.MP_Backend.dto.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {
    //@GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name= SessionConst.LOGIN_MEMBER, required = false) MemberDTO loginMember, Model model) {
        //세션에 회원 데이터가 없으면 home으로
        if (loginMember == null){
            return "index";
        }
        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginIndex";
    }
}
