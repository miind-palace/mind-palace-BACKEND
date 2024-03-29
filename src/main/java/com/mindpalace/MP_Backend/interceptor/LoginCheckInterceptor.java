package com.mindpalace.MP_Backend.interceptor;

import com.mindpalace.MP_Backend.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 {}", requestURI);
        HttpSession session = request.getSession(false);

        log.info("세션값 들어오는지 확인" + session);

        if (session != null){
            session.getAttributeNames().asIterator()
                    .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));
        }

        if (session == null || session.getAttribute(SessionConst.LOGIN_ID) == null){
            log.info("미인증 사용자 요청");
            //로그인으로 redirect
            response.sendRedirect("/member/login?redirectURL=" + requestURI);
            return false; //더 진행 안 하겠다.
        }
        return true;
    }
}
