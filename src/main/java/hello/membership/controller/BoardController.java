package hello.membership.controller;

import hello.membership.domain.Board;
import hello.membership.exception.exception.UserException;
import hello.membership.service.BoardService;
import hello.membership.web.const_.SessionConst;
import hello.membership.web.dto.BoardDTO;
import hello.membership.web.form.BoardForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String mainPage(Model model) {
        List<Board> boards = boardService.getAllBoard();
        model.addAttribute("boards", boards);
        return "board";
    }

    @GetMapping("/write")
    public String writePage() {
        return "write";
    }

    @ResponseBody
    @PostMapping("/write")
    public BoardDTO write(
            @Validated @RequestBody BoardForm boardForm,
            BindingResult bindingResult,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId
    ) {

        if (bindingResult.hasErrors()) {
            throw new UserException("글 작성시 잘못된 값 입력");
        }

        Board board = boardService.save(boardForm, memberId);
        log.info("save board.id={} board.title={} board.content={}", board.getId(), board.getTitle(), board.getContent());
        return new BoardDTO(board.getId());
    }

    @GetMapping("/test")
    public String sessionIdCheck(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId
    ) {
        log.info("memberId={}", memberId);
        return "ok";
    }
}
