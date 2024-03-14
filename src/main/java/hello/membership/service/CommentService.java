package hello.membership.service;

import hello.membership.domain.Board;
import hello.membership.domain.Comment;
import hello.membership.domain.Member;
import hello.membership.repository.BoardRepository;
import hello.membership.repository.CommentRepository;
import hello.membership.repository.MemberRepository;
import hello.membership.web.form.CommentForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Long save(CommentForm form, Long memberId, Long boardId) {

        Member member = memberRepository.findById(memberId);
        Board board = boardRepository.findById(boardId);

        Comment comment = Comment.createComment(member, board);
        comment.setContent(form.getContent());
        commentRepository.save(comment);

        return comment.getId();
    }

    public List<Comment> findBoardComments(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }
}
