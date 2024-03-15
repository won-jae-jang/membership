package hello.membership.controller;

import hello.membership.domain.Board;
import hello.membership.domain.Comment;
import hello.membership.exception.exception.NotAuthorizedUserException;
import hello.membership.exception.exception.UserException;
import hello.membership.service.BoardService;
import hello.membership.service.CommentService;
import hello.membership.web.const_.SessionConst;
import hello.membership.web.dto.AuthorizedDTO;
import hello.membership.web.dto.BoardDTO;
import hello.membership.web.dto.DeleteBoardDTO;
import hello.membership.web.dto.UpdateBoardDTO;
import hello.membership.web.form.BoardForm;
import hello.membership.web.form.UpdateBoardForm;
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
    private final CommentService commentService;

    @GetMapping
    public String mainPage(Model model) {
        List<Board> boards = boardService.getAllBoard();
        model.addAttribute("boards", boards);
        return "board";
    }

    /**
     * 글 작성 화면 및 로직
     */

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

    /**
     * 게시판 화면
     */
    @GetMapping("/{boardId}")
    public String boardPage(@PathVariable Long boardId, Model model) {

        Board board = boardService.findById(boardId);
        String formatDate = boardService.formatDate(board.getDate());
        List<Comment> comments = commentService.findBoardComments(boardId);

        model.addAttribute("board", board);
        model.addAttribute("formatDate", formatDate);
        model.addAttribute("comments", comments);

        boardService.upView(boardId); //조회수 올리기
        return "boardDetail";
    }

    @GetMapping("/update-check-authorized")
    @ResponseBody
    public AuthorizedDTO checkUpdateAuthorized(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
            @RequestParam(name = "boardId") Long boardId,
            Model model
    ) {
        if (boardService.isAuthorized(memberId, boardId)){
            return new AuthorizedDTO(true);
        }
        return new AuthorizedDTO(false);
    }

    @GetMapping("/update")
    public String updateBoardForm(@RequestParam(name = "boardId") Long boardId, Model model) {
        Board board = boardService.findById(boardId);
        model.addAttribute("board", board);
        return "editBoard";
    }

    @PostMapping("/update/{boardId}")
    @ResponseBody
    public UpdateBoardDTO updateBoard(
            @Validated @RequestBody UpdateBoardForm form, BindingResult bindingResult,
            @PathVariable(name = "boardId") Long boardId)
    {
        if (bindingResult.hasErrors()) {
            throw new UserException("글 업데이트 사용자 입력 오류");
        }
        boardService.updateBoard(form, boardId);
        return new UpdateBoardDTO();
    }

    @PostMapping("/delete/{boardId}")
    @ResponseBody
    public DeleteBoardDTO deleteBoard(
        @PathVariable(name = "boardId") Long boardId,
        @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId
    ){
        if (boardService.isAuthorized(memberId, boardId)){
            boardService.deleteBoard(boardId);
            return new DeleteBoardDTO(true, "게시판 삭제 성공");
        }
        return new DeleteBoardDTO(false, "게시판 삭제 실패");
    }

    @GetMapping("/test")
    public String sessionIdCheck(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId
    ) {
        log.info("memberId={}", memberId);
        return "ok";
    }
}
