package hello.membership.controller;

import hello.membership.controller.dto.MemberDTO;
import hello.membership.domain.Member;
import hello.membership.exception.exception.LoginFailException;
import hello.membership.exception.exception.UserException;
import hello.membership.service.LoginService;
import hello.membership.web.const_.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
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
    @ResponseBody
    public MemberDTO login(@RequestBody @Validated Member member, BindingResult bindingResult, HttpServletRequest request) {

        log.info("login: member.getUsername={}, member.getPassword={}", member.getUsername(), member.getPassword());

        if (bindingResult.hasErrors()) {
            log.error("login error={}", bindingResult);
            throw new UserException("로그인 입력 오류");
        }

        Member loginMember = loginService.login(member.getUsername(), member.getPassword());
        if (loginMember == null) {
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            throw new LoginFailException("로그인 실패");
        }

        //로그인 성공 처리

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember.getId());

        //성공 메시지 반환
        MemberDTO loginDTO = new MemberDTO(loginMember);

        return loginDTO;
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

//    @GetMapping("/join")
    public String joinForm() {
        return "join";
    }

    @ResponseBody
    @PostMapping("/join")
    public MemberDTO join(@RequestBody @Valid Member member, BindingResult bindingResult) {
        log.info("LoginController.save");

        if (bindingResult.hasErrors()) {
            log.error("save error={}", bindingResult);
            throw new UserException("잘못된 입력입니다");
        }

        log.info("join member.username = {} member.password = {}", member.getUsername(), member.getPassword());
        loginService.save(member);
        MemberDTO memberDTO = new MemberDTO(member);
        return memberDTO;
    }
}
