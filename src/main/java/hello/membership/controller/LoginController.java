package hello.membership.controller;

import hello.membership.domain.Member;
import hello.membership.repository.MemberRepository;
import hello.membership.service.LoginService;
import hello.membership.web.const_.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Getter
@Setter
@Slf4j
@RequestMapping("/members")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm() {
        log.info("---- login controller ----");
        return "login.html";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Validated Member member, BindingResult bindingResult, HttpServletRequest request) {

        log.info("login: member.getUsername={}, member.getPassword={}", member.getUsername(), member.getPassword());

        if (bindingResult.hasErrors()) {
            log.error("login error={}", bindingResult);
            return "login";
        }

        Member loginMember = loginService.login(member.getUsername(), member.getPassword());
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "redirect:/members/login";
        }

        //로그인 성공 처리

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션 삭제
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/join")
    public String joinForm() {
        return "join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute @Validated Member member, BindingResult bindingResult) {
        System.out.println("LoginController.save");

        if (bindingResult.hasErrors()) {
            log.error("save error={}", bindingResult);
            return "join";
        }

        log.info("join member.username = {} member.password = {}", member.getUsername(), member.getPassword());
        loginService.save(member);
        return "redirect:/";
    }
}
