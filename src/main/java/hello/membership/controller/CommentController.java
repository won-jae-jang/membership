package hello.membership.controller;

import hello.membership.domain.Comment;
import hello.membership.exception.exception.UserException;
import hello.membership.service.CommentService;
import hello.membership.web.const_.SessionConst;
import hello.membership.web.dto.CommentDTO;
import hello.membership.web.form.CommentForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}")
    @ResponseBody
    public CommentDTO comment(
            @Validated @RequestBody CommentForm form,
            BindingResult bindingResult,
            @PathVariable(name = "boardId") Long boardId,
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId
    ) {
        log.info("----comment controller----");
        if (bindingResult.hasErrors()){
            throw new UserException("댓글 사용자 오류");
        }

        commentService.save(form, memberId, boardId);
        return new CommentDTO();
    }
}
