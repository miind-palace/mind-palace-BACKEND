package com.mindpalace.MP_Backend.controller;

import com.mindpalace.MP_Backend.SessionConst;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@Api(tags = {"홈 화면 접근용 컨트롤러"})
public class HomeController {
    //@GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null){
            return "index";
        }
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

        String loginEmail = (String)session.getAttribute(SessionConst.LOGIN_EMAIL);

        log.info("loginEmail= {}" + loginEmail);

        //세션에 회원 데이터가 없으면 home으로
        if (loginEmail == null){
            return "index";
        }

        return "loginIndex";
    }
}