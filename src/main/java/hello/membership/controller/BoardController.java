package hello.membership.controller;

import hello.membership.domain.Board;
import hello.membership.service.BoardService;
import hello.membership.web.const_.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String mainPage() {
        return "board";
    }

    @GetMapping("/test")
    public String sessionIdCheck(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId
    ) {
        log.info("memberId={}", memberId);
        return "ok";
    }
}
