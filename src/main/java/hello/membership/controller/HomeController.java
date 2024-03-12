package hello.membership.controller;

import hello.membership.domain.Member;
import hello.membership.web.const_.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId
    ) {

        //세션 없으면 로그인 화면으로 이동
        if (memberId == null) {
            return "login";
        }

        return "home";
    }
}
